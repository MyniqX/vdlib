package com.vdprime.vdlib.utils;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burak on 19.07.2017.
 */
public class vdio
{
    public static CompressFormat defaultCompressFormat  = CompressFormat.WEBP;
    public static int            defaultCompressQuality = 90;
    
    public static final List<String> readAllLinesAsList(File file)
    {
        BufferedReader reader = null;
        List<String>   list   = new ArrayList<>(1024);
        try
        {
            reader = new BufferedReader(new FileReader(file), 1024 * 1024 * 4);
            String line;
            while ((line = reader.readLine()) != null) list.add(line);
        }
        catch (Exception e)
        {
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (Exception p)
            {
            }
        }
        return list;
    }
    
    public static final void writeStreamToFile(File file, InputStream stream)
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int    len;
            while ((len = stream.read(buffer)) != -1)
            {
                fos.write(buffer, 0, len);
            }
        }
        catch (Exception e)
        {
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (Exception e)
            {
            }
        }
    }
    
    public static final void writeAllBytes(File file, byte[] data)
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
            fos.write(data);
        }
        catch (Exception e)
        {
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (Exception e)
            {
            }
        }
    }
    
    public static final String readAllText(File file)
    {
        // return vdstring.Join("\n", readAllLinesAsList(file));
        if (file.exists() == false) return "";
        byte[] data = readAllBytes(file);
        return data == null ? "" : new String(data);
    }
    
    public static final byte[] readAllBytes(File file)
    {
        FileChannel channel = null;
        byte[]      result  = null;
        try
        {
            channel = new FileInputStream(file).getChannel();
            result = new byte[(int) file.length()];
            ByteBuffer bf = ByteBuffer.wrap(result);
            bf.order(ByteOrder.LITTLE_ENDIAN);
            channel.read(bf);
            //    MappedByteBuffer buffer = channel.map(MapMode.READ_ONLY, 0, channel.size());
            //    result = buffer.array();
        }
        catch (Exception e)
        {
            String              msg = e.getMessage();
            StackTraceElement[] st  = e.getStackTrace();
            String              a   = "1";
        }
        finally
        {
            try
            {
                channel.close();
            }
            catch (Exception e)
            {
            }
        }
        return result;
    }
    
    public static final void writeAllText(File file, String data)
    {
        FileWriter fw = null;
        try
        {
            fw = new FileWriter(file);
            fw.write(data);
            fw.close();
        }
        catch (Exception e)
        {
        }
        finally
        {
            try
            {
                fw.close();
            }
            catch (Exception e)
            {
            }
        }
    }
    
    public static void deleteRecursive(File fileOrDirectory)
    {
        if (fileOrDirectory.isDirectory())
        {
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);
        }
        fileOrDirectory.delete();
    }
    
    public static void saveBMPFile(File file, Bitmap bmp)
    {
        saveBMPFile(file, bmp, defaultCompressFormat, defaultCompressQuality);
    }
    
    public static void saveBMPFile(File file, Bitmap bmp, CompressFormat frm, int quality)
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
            bmp.compress(frm, quality, fos);
        }
        catch (Exception e)
        {
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (Exception e)
            {
            }
        }
    }
}
