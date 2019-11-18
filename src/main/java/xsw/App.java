package xsw;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.client.*;

import io.fabric8.kubernetes.client.Watch;
import io.fabric8.kubernetes.client.Watcher;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class App {


    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch closeLatch = new CountDownLatch(1);
        String kubernetesMaster = "https://cci.cn-north-1.myhuaweicloud.com";
        Config config = new ConfigBuilder().withMasterUrl(kubernetesMaster).build();
        try (final KubernetesClient client = new DefaultKubernetesClient(config)) {
            try (Watch watch = client.configMaps().inNamespace("cci-wulei").watch(new Watcher<ConfigMap>() {
                @Override
                public void eventReceived(Action action, ConfigMap resource) {
                    System.out.println("event " + action.name() + " " + resource.toString());
                }

                @Override
                public void onClose(KubernetesClientException e) {
                    System.out.println("Watcher onClose");
                    if (e != null) {
                        System.out.printf(e.getMessage(), e);
                        closeLatch.countDown();
                    }
                }
            })) {
                closeLatch.await(10, TimeUnit.SECONDS);
            } catch (KubernetesClientException | InterruptedException e) {
                System.out.printf("Could not watch resources", e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf(e.getMessage(), e);

            Throwable[] suppressed = e.getSuppressed();
            if (suppressed != null) {
                for (Throwable t : suppressed) {
                    System.out.printf(t.getMessage(), t);
                }
            }
        }
        Thread.sleep(600000000l);
    }

}
