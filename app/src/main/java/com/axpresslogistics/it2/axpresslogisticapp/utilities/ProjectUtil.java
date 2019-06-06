package com.axpresslogistics.it2.axpresslogisticapp.utilities;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.axpresslogistics.it2.axpresslogisticapp.R;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.codec.binary.Hex;


public class ProjectUtil {


    public static String getMyDeviceVersion() {
        //Build.VERSION.RELEASE
        return "" + Build.VERSION.RELEASE;
    }


    public static String getCurrentTime() {
        DateFormat sdf = new SimpleDateFormat("hh:mm");
        Date date = new Date();
        return sdf.format(date);

    }

    public static void customToast(Context context, String message) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tost, null);
        TextView txt_msg = (TextView) view.findViewById(R.id.txt_message);
        txt_msg.setText(message);
        txt_msg.setTextColor(context.getResources().getColor(R.color.white));
        //Creating the Toast object
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(view);//setting the view of custom toast simple_spinner
        toast.show();
    }

    public static void customDoenToast(Context context, String message) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tost, null);
        TextView txt_msg = (TextView) view.findViewById(R.id.txt_message);
        txt_msg.setText(message);
        txt_msg.setTextColor(Color.parseColor("#099908"));
        //Creating the Toast object
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(view);//setting the view of custom toast simple_spinner
        toast.show();
    }

    public static String getIp() {
        String ip = "";
        ip = NativeHelper.getIPAddress(true);
        if (ip != null && !ip.equalsIgnoreCase("")) {
            return ip;
        } else {
            ip = NativeHelper.getIPAddress(false);
            return ip;
        }
    }


    public static String newMd5(String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String AESEncrypt(String myKey, String strToEncrypt) {
        try {
            final Cipher cipher;
            final SecretKeySpec key;
            AlgorithmParameterSpec spec;
            final String SEED_16_CHARACTER = myKey; //"U1MjU1M0FDOUZ.Qz";
            // hash password with SHA-256 and crop the output to 128-bit for key
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(SEED_16_CHARACTER.getBytes("UTF-8"));
            byte[] keyBytes = new byte[32];
            System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);

            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            key = new SecretKeySpec(keyBytes, "AES");
            spec = getIV();
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            byte[] encrypted = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));
            String encryptedText = new String(Base64.encode(encrypted, Base64.NO_WRAP), "UTF-8");
            return encryptedText;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static AlgorithmParameterSpec getIV() {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);

        return ivParameterSpec;
    }


    public static boolean validatePanNo(String panNo) {
        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        Matcher matcher = pattern.matcher(panNo);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validatePassword(String pwd) {
        String pwdPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$";
        Pattern pattern = Pattern.compile(pwdPattern);
        Matcher matcher = pattern.matcher(pwd);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean validGSTIN(String gstin) {
        Pattern pattern = Pattern.compile("[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9A-Za-z]{1}[Z]{1}[0-9a-zA-Z]{1}");
        Matcher matcher = pattern.matcher(gstin);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }

    }

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(calendar.getTime()).toString();
    }


    public static String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(calendar.getTime()).toString();
    }

    public static String getAadhaarNo() {
        String uidai_no = "XXXX-XXXX-" + "1234";
        return uidai_no;
    }


    public static String getDecimalFormated(String val) {
        String value = null;
        if (val != null && !val.equalsIgnoreCase("")) {
            Double data = Double.parseDouble(val);
            Float f = new Float(Math.round(data));
            value = String.format("%.2f", f);
        } else {
            value = "0";
        }
        return value;
    }

    public static String getRoundOff(String val) {
        Double data = Double.parseDouble(val);
        return String.valueOf(new Float(Math.round(data)));

    }


    public static float getCommissionDeposit(String amt) {
        float comm = 0;
        try {
            float amount = Float.parseFloat(amt);
            if (amount >= 500 && amount <= 999) {
                comm = 1.5f;
            } else if (amount >= 1000 && amount <= 2999) {
                comm = 6f;
            } else if (amount >= 3000 && amount <= 10000)

            {
                comm = 14f;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return comm;
    }

    public static float getCommissionWithdrawal(String amt) {
        float comm = 0;
        try {
            float amount = Float.parseFloat(amt);
            if (amount >= 1000 && amount <= 2999) {
                comm = 4f;
            } else if (amount >= 3000 && amount <= 10000) {
                comm = 10f;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return comm;
    }


    public static float getCommissionBE() {
        float comm = 1f;
        return comm;
    }


    public static String getMapKey(Map<String, String> mapref, String value) {
        String key = "";
        for (Map.Entry<String, String> map : mapref.entrySet()) {
            if (map.getValue().toString().equals(value)) {
                key = map.getKey();
            }
        }
        return key;
    }


    public static String encodeBase64(String text) {
        try {
            byte[] data = text.getBytes("UTF-8");
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);
            return base64;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decodeBase64(String base64) {
        try {
            byte[] data = Base64.decode(base64, Base64.DEFAULT);
            String text = new String(data, "UTF-8");
            return text;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String b2h(byte[] bytes) {
        char[] hex = new char[bytes.length * 2];
        for (int idx = 0; idx < bytes.length; ++idx) {
            int hi = (bytes[idx] & 0xF0) >>> 4;
            int lo = (bytes[idx] & 0x0F);
            hex[idx * 2] = (char) (hi < 10 ? '0' + hi : 'A' - 10 + hi);
            hex[idx * 2 + 1] = (char) (lo < 10 ? '0' + lo : 'A' - 10 + lo);
        }
        Log.d("Encrypted Pin : ", new String(hex));
        return new String(hex);
    }

    public static String getAua() {
        return "" + System.currentTimeMillis();
    }





   /* public static boolean validateAadharNumber(String aadharNumber){
        Pattern aadharPattern = Pattern.compile("\\d{12}");
        boolean isValidAadhar = aadharPattern.matcher(aadharNumber).matches();
        if(isValidAadhar){
            isValidAadhar = VerhoeffAlgorithm.validateVerhoeff(aadharNumber);
        }
        return isValidAadhar;
    }*/

}
