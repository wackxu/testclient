package xsw;


import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );
        String kubernetesMaster = "https://cci.cn-north-1.myhuaweicloud.com";
        Config config = new ConfigBuilder().withMasterUrl(kubernetesMaster).build();
        KubernetesClient client = new DefaultKubernetesClient(config);
        NamespaceList myNs = client.namespaces().list();
        System.out.println(myNs);
    }
}
