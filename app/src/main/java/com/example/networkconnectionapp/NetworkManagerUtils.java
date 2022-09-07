package com.example.networkconnectionapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class NetworkManagerUtils {

    /**
     * Get the network info
     *
     * @param context
     * @return
     */
    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if there is any connectivity
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = NetworkManagerUtils.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static boolean isConnectedWifi(Context context) {
        NetworkInfo info = NetworkManagerUtils.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = NetworkManagerUtils.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Check if there is fast connectivity
     *
     * @param context
     * @return
     */
    public static boolean isConnectedFast(Context context) {
        NetworkInfo info = NetworkManagerUtils.getNetworkInfo(context);
        return (info != null && info.isConnected() && NetworkManagerUtils.isConnectionFast(info.getType(), info.getSubtype()));
    }

    /**
     * Check if the connection is fast
     *
     * @param type
     * @param subType
     * @return
     */
    public static boolean isConnectionFast(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
                /*
                 * Above API level 7, make sure to set android:targetSdkVersion
                 * to appropriate level to use these
                 */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return true; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    public static boolean checkInternetConnection(Context context) {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            Toast.makeText(context, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED) {
            Toast.makeText(context, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    public static String getNetworkClass(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
            return "-"; // not connected
        if (info.getType() == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS: {
                    return "GPRS";
                }
                case TelephonyManager.NETWORK_TYPE_EDGE: {
                    return "EDGE";
                }
                case TelephonyManager.NETWORK_TYPE_CDMA: {
                    return "CDMA";
                }
                case TelephonyManager.NETWORK_TYPE_1xRTT: {
                    return "SINGLE CARRIER RADIO TRANSMISSION";
                }
                case TelephonyManager.NETWORK_TYPE_IDEN: {
                    return "INTEGRATED DIGITAL ENHANCED NETWORK";
                }     // api< 8: replace by 11
                case TelephonyManager.NETWORK_TYPE_GSM:      // api<25: replace by 16
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS: {
                    return "3G";
                }
                case TelephonyManager.NETWORK_TYPE_EVDO_0: {
                    return "EVOLUTION DATA ONLY 0";
                }
                case TelephonyManager.NETWORK_TYPE_EVDO_A: {
                    return "EVOLUTION DATA ONLY A";
                }
                case TelephonyManager.NETWORK_TYPE_HSDPA: {
                    return "High-Speed Downlink Packet Access";
                }
                case TelephonyManager.NETWORK_TYPE_HSUPA: {
                    return "High-Speed Uplink Packet Access";
                }
                case TelephonyManager.NETWORK_TYPE_HSPA: {
                    return "High-Speed Packet Access";
                }
                case TelephonyManager.NETWORK_TYPE_EVDO_B: {
                    return "EVOLUTION DATA ONLY B";
                }
                case TelephonyManager.NETWORK_TYPE_EHRPD: {
                    return "Enhanced High-Rate Packet Data";
                }    // api<11: replace by 14
                case TelephonyManager.NETWORK_TYPE_HSPAP: {
                    return "High Speed Packet Access Plus";
                }    // api<13: replace by 15
                case TelephonyManager.NETWORK_TYPE_TD_SCDMA: // api<25: replace by 17
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE: {
                    return "LTE";
                }
                case TelephonyManager.NETWORK_TYPE_IWLAN:    // api<25: replace by 18
                case 19: // LTE_CA
                    return "4G";
                default:
                    return "?";
            }
        }
        return "?";
    }
}
