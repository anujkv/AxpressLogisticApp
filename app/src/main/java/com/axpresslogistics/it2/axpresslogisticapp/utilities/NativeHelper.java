package com.axpresslogistics.it2.axpresslogisticapp.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;


public class NativeHelper {

    /**
     * @param context
     * @return
     * @author Gunjan
     * function is used to detect
     * network is available or not
     */
    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null)
            return false;
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        return isConnected;
    }

    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }

    }


    /**
     * Convert byte array to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sbuf = new StringBuilder();
        for (int idx = 0; idx < bytes.length; idx++) {
            int intVal = bytes[idx] & 0xff;
            if (intVal < 0x10) sbuf.append("0");
            sbuf.append(Integer.toHexString(intVal).toUpperCase());
        }
        return sbuf.toString();
    }

    /**
     * Get utf8 byte array.
     *
     * @param str
     * @return array of NULL if error was found
     */
    public static byte[] getUTF8Bytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Load UTF8withBOM or any ansi text file.
     *
     * @param filename
     * @return
     * @throws java.io.IOException
     */
    public static String loadFileAsString(String filename) throws java.io.IOException {
        final int BUFLEN = 1024;
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(filename), BUFLEN);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFLEN);
            byte[] bytes = new byte[BUFLEN];
            boolean isUTF8 = false;
            int read, count = 0;
            while ((read = is.read(bytes)) != -1) {
                if (count == 0 && bytes[0] == (byte) 0xEF && bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF) {
                    isUTF8 = true;
                    baos.write(bytes, 3, read - 3); // drop UTF8 bom marker
                } else {
                    baos.write(bytes, 0, read);
                }
                count += read;
            }
            return isUTF8 ? new String(baos.toByteArray(), "UTF-8") : new String(baos.toByteArray());
        } finally {
            try {
                is.close();
            } catch (Exception ex) {
            }
        }
    }

    /**
     * Returns MAC address of the given interface name.
     *
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return mac address or empty string
     */
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null) return "";
                StringBuilder buf = new StringBuilder();
                for (int idx = 0; idx < mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length() > 0) buf.deleteCharAt(buf.length() - 1);
                return buf.toString();
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
        /*try {
            // this is so Linux hack
            return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
        } catch (IOException ex) {
            return null;
        }*/
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }
    /**
     *
     * @param context
     * @param number
     */
/*	public static void makeCall(Context context, String number)
	{
		Intent call		=new Intent(Intent.ACTION_CALL);
		call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		call.setData(Uri.parse("tel:"+number));
		context.startActivity(call);
	}
	*//**
     * check is there camera exists or not in device
     * @param context
     * @return
     *//*
	public static  boolean isCameraAvailable(Context context) {
		PackageManager packageManager=context.getPackageManager();
	    if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
	        // this device has a camera
	        return true;
	    } else {
	        // no camera on this device
	        return false;
	    }
	}
	
	@SuppressLint("NewApi")
	public static boolean isCameraAvailbles(Context context)
	{
		int numCameras = Camera.getNumberOfCameras();
		if (numCameras > 1) {
			return true;
		}
		return false;
			
	}
	
	public static boolean isCameraAvailableInDevice(Context context)
	{
		PackageManager pm=context.getPackageManager();
		boolean frontCam, rearCam;
		//Must have a targetSdk >= 9 defined in the AndroidManifest
		frontCam 	= pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
		rearCam 	= pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
		if(!frontCam && !rearCam)
		{
			return false;
		}
		return true;
	}
	
	*//**
     * check network Location enable or not
     *//*
	public static  boolean isGpsEnable(Context context)
	{
		LocationManager lm = (LocationManager)context. getSystemService(Context.LOCATION_SERVICE);
		//if(Build.VERSION>VERSION.SDK_INT
		if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			return true;
		}
		return false;
	}
	public static boolean isNetworkProviderEnable(Context context)
	{
		LocationManager lm = (LocationManager)context. getSystemService(Context.LOCATION_SERVICE);
		if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
		{
			return true;
		}
		return false;
	}
	public boolean isLocationProviderEnable(final Context context)
	{
		if(!isGpsEnable(context) || !isNetworkProviderEnable(context)) {
			  // Build the alert dialog
			  AlertDialog.Builder builder = new AlertDialog.Builder(context);
			  builder.setTitle("Location Services Not Active");
			  builder.setMessage("Please enable Location Services and GPS");
			  builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
			  {
			  public void onClick(DialogInterface dialogInterface, int i) {
			    // Show location settings when the user acknowledges the alert dialog
				  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				  context.startActivity(intent);
			    }
			  });
			  Dialog alertDialog = builder.create();
			  alertDialog.setCanceledOnTouchOutside(false);
			  alertDialog.show();
			  return false;
			}
		return true;
	}
	
	*//**
     * calculate distance between Two Points
     *//*
	public static double CalculationByDistance(LatLng StartP, LatLng EndP) 
	{
		
        int Radius=6371;//radius of earth in Km         
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
        Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult= Radius*c;
        double km=valueResult/1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec =  Integer.valueOf(newFormat.format(km));
        double meter=valueResult%1000;
        int  meterInDec= Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value",""+valueResult+"   KM  "+kmInDec+" Meter   "+meterInDec);

        return Radius * c;
        
     }
		public static boolean  googleplaystatus(Context context)
		{
			int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
			// Showing status
	        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
	        
	        	return false;
	        }
			return true;
		}
	      
		
      public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
		    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
		        if (serviceClass.getName().equals(service.service.getClassName())) {
		            return true;
		        }
		    }
		    return false;
		}


    /**
     * @param context
     * @return get my unique device ID
     */
    public static String getMyDeviceID(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public static String getMyDeviceVersion() {//Build.VERSION.RELEASE

        return "" + (Build.VERSION.RELEASE);
    }


}
