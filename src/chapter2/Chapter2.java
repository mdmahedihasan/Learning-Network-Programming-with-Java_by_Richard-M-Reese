package chapter2;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

public class Chapter2 {

    public static void main(String[] args) {
        usingTheNetworkInterfaceClass();
        gettingMACAddress();
        creatingURIInstances();
        usingTheURLClass();
        obtainingInformationAboutAnIPAddress();
        testingReachability();
        theInet4AddressClass();
        testingForTheIPAddressType();
        usingIPv4CompatibleIPv6Addresses();
        isIPv4CompatibleAddressExample();
    }

    private static void usingTheNetworkInterfaceClass() {
        try {
            Enumeration<NetworkInterface> interfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            System.out.printf("Name     Display name\n");
            for (NetworkInterface element : Collections.list(interfaceEnumeration)) {
                System.out.printf("%-8s %-32s\n", element.getName(), element.getDisplayName());

                Enumeration<InetAddress> addressEnumeration = element.getInetAddresses();
                for (InetAddress inetAddress : Collections.list(addressEnumeration)) {
                    System.out.printf("     InetAddress : %s\n", inetAddress);
                }

                addressEnumeration = element.getInetAddresses();
                Collections
                        .list(addressEnumeration)
                        .stream()
                        .forEach((inetAddress -> {
                            System.out.printf("     InetAddress : %s\n", inetAddress);
                        }));
            }
            /**interfaceEnumeration = NetworkInterface.getNetworkInterfaces();
             for (NetworkInterface element : Collections.list(interfaceEnumeration)
             ) {
             displayInterfaceInformation(element);
             }**/
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private static String getMACIdentifier(NetworkInterface networkInterface) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            byte[] bytes = networkInterface.getHardwareAddress();
            if (bytes != null) {
                for (int i = 0; i < bytes.length; i++) {
                    stringBuilder.append(String.format("%02X%s", bytes[i], (i < bytes.length - 1) ? "-" : ""));
                }
            } else {
                return "---";
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private static void gettingMACAddress() {
        try {
            /**InetAddress inetAddress;
             inetAddress = InetAddress.getLocalHost();
             System.out.println("IP address : " + inetAddress.getHostAddress());
             NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inetAddress);
             System.out.println("MAC address : " + getMACIdentifier(networkInterface));**/

            Enumeration<NetworkInterface> interfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            System.out.println("Name        MAC Address");
            for (NetworkInterface element : Collections.list(interfaceEnumeration)) {
                System.out.printf("%-6s %s\n", element.getName(), getMACIdentifier(element));
            }

            System.out.println("-----Java 8-----");
            interfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            Collections
                    .list(interfaceEnumeration)
                    .stream()
                    .forEach((networkInterface) -> {
                        System.out.printf("%-6s %s\n", networkInterface.getName(), getMACIdentifier(networkInterface));
                    });
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private static void displayURI(URI uri) {
        System.out.println("URI Information");
        System.out.println("getAuthority : " + uri.getAuthority());
        System.out.println("getScheme : " + uri.getScheme());
        System.out.println("getSchemeSpecificPart : " + uri.getSchemeSpecificPart());
        System.out.println("getHost : " + uri.getHost());
        System.out.println("getPath : " + uri.getPath());
        System.out.println("getQuery : " + uri.getQuery());
        System.out.println("getFragment : " + uri.getFragment());
        System.out.println("getUserInfo : " + uri.getUserInfo());
        System.out.println("normalize : " + uri.normalize());
    }

    private static void creatingURIInstances() {
        try {
            //URI uri = new URI("https://www.packtpub.com/books/content/support");
            //URI uri = new URI("https://en.wikipedia.org/wiki/" + "URL_normalization#Normalization_process");
            URI uri = new URI("https", "en.wikipedia.org", "/wiki/URL_normalization", "Normalization_process");
            displayURI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static void displayURL(URL url) {
        System.out.println("URL : " + url);
        System.out.printf("    Protocol : %-32s  Host : %-32s\n", url.getProtocol(), url.getHost());
        System.out.printf("     Port : %-32d  Path : %-32s\n", url.getPort(), url.getPath());
        System.out.printf("Reference : %-32s File : %-32s\n", url.getRef(), url.getFile());
        System.out.printf("Authority : %-32s Query : %-32s\n", url.getAuthority(), url.getQuery());
        System.out.println("User Info : " + url.getUserInfo());
    }

    private static void usingTheURLClass() {
        try {
            //URL url = new URL("https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=url+syntax");
            URL url = new URL("http://www.packtpub.com");
            System.out.println();
            displayURL(url);
            System.out.println("getContent : " + url.getContent());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayInetAddressInformation(InetAddress inetAddress) {
        System.out.println("-----InetAddress Information-----");
        System.out.println(inetAddress);
        System.out.println("CanonicalHostName : " + inetAddress.getCanonicalHostName());
        System.out.println("HostName : " + inetAddress.getHostName());
        System.out.println("HostAddress : " + inetAddress.getHostAddress());
        System.out.println("------------------------");
    }

    private static void obtainingInformationAboutAnIPAddress() {
        try {
            InetAddress inetAddress = InetAddress.getByName("www.google.com");
            displayInetAddressInformation(inetAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void testingReachability() {
        try {
            String urlAddress = "www.google.com";
            InetAddress[] inetAddresses = InetAddress.getAllByName(urlAddress);
            for (InetAddress inetAddress : inetAddresses) {
                try {
                    if (inetAddress.isReachable(10000)) {
                        System.out.println(inetAddress + "is reachable");
                    } else {
                        System.out.println(inetAddress + " is not reachable");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void theInet4AddressClass() {
        try {
            Inet4Address inet4Address;
            inet4Address = (Inet4Address) Inet4Address.getByName("www.google.com");
            //inet4Address = (Inet4Address) InetAddress.getByName("www.google.com");
            System.out.println(inet4Address.isAnyLocalAddress());
            System.out.println(inet4Address.isLoopbackAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void testingForTheIPAddressType() {
        try {
            InetAddress inetAddress = InetAddress.getByName("www.google.com");

            byte[] bytes = inetAddress.getAddress();
            if (bytes.length <= 4) {
                System.out.println("IPv4 Address");
            } else {
                System.out.println("IPv6 Address");
            }

            if (inetAddress instanceof Inet4Address) {
                System.out.println("IPv4 Address");
            } else {
                System.out.println("IPv6 Address");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void usingIPv4CompatibleIPv6Addresses() {
        try {
            InetAddress inetAddress = InetAddress.getByName("www.google.com");
            displayInetAddressInformation(inetAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void isIPv4CompatibleAddressExample() {
        try {
            InetAddress[] names = InetAddress.getAllByName("www.google.com");
            for (InetAddress inetAddress : names) {
                if ((inetAddress instanceof Inet6Address) && ((Inet6Address) inetAddress).isIPv4CompatibleAddress()) {
                    System.out.println(inetAddress + " is IPv4 Compatible Address");
                } else {
                    System.out.println(inetAddress + " is not IPv4 Compatible Address");
                }
            }

            System.out.println("---Java 8---");
            names = Inet6Address.getAllByName("www.google.com");
            Arrays
                    .stream(names)
                    .map(inetAddress -> {
                        if ((inetAddress instanceof Inet6Address) && ((Inet6Address) inetAddress)
                                .isIPv4CompatibleAddress()) {
                            return inetAddress + " is IPv4 Compatible Address";
                        } else {
                            return inetAddress + " is not IPv4 Compatible Address";
                        }
                    })
                    .forEach(result -> System.out.println(result));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
