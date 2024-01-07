package de.marketsim.util;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class FileTool
{

                /**
                 * create a new directory under the current directory where the java is started.
                 * @param pDirName
                 */
            public static void createDirectory (String pDirName)
            {
                try
                {
                    File  ff = new File( pDirName );
                    ff.mkdir();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }

                /**
                 * create a new directory in the specified parent directory.
                 * @param pDirName
                 */
            public static void createDirectoryInSpecifiedDir (String pSpecifiedParentDir, String pDirName)
            {
                try
                {
                    File  ff = new File(pSpecifiedParentDir,  pDirName );
                    ff.mkdirs();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }

            public static void CreateEmptyFile(String pFileName)
            {
              try
              {
                 FileOutputStream   ff = new FileOutputStream( pFileName, true );
                 ff.close();
              }
              catch (Exception ex)
              {
                ex.printStackTrace();
              }
            }

            public static void DeleteFile(String pFileName)
            {

                  File  ff = new File( pFileName );
                  boolean deleted = ff.delete();
                  if ( deleted )
                  {
                  System.out.println( "File is "+ pFileName + " is deleted.");
                  }
                  else
                  {
                    System.out.println( "File "+ pFileName + " is not deleted.");
                  }
            }

            public static void checkDir(String pDirName)
            {
              File ff = new File(pDirName);
              File tempff[] = ff.listFiles();
              if ( tempff != null )
              {
                for (int i=0; i<tempff.length;i++)
                {
                   System.out.println(  tempff[i].getName() );
                   DeleteFile( tempff[i].getName() );
                }
              }
            }

            public static void DeleteOldDepotFile(String pDirName)
            {
                 File ff = new File(pDirName);
                 File tempff[] = ff.listFiles();
                 if ( tempff != null )
                 {
                   for (int i=0; i<tempff.length;i++)
                   {
                      String fname = tempff[i].getName().toUpperCase();
                      if ( fname.startsWith("F") || fname.startsWith("N") || fname.startsWith("R"))
                      {
                         tempff[i].delete();
                      }
                   }
                 }
            }

            public static String removeDirectoryName(String pAbsoluteFileName )
            {
                 String ss1 =pAbsoluteFileName;
                 ss1 = ss1.replace('\\', '/');

                 int j = ss1.indexOf("/");
                 String ss2 = ss1;
                 while ( j>=0 )
                 {
                    ss2 = ss2.substring( j +1 );
                    j = ss2.indexOf("/");
                 }
                 return ss2;
            }

            public static void displayFilesOfDirectory(String pParentDir,  String pDirName)
            {
                    try
                    {
                     File ff = new File(pParentDir, pDirName );
                     String ss[] = ff.list();
                     for ( int i=0; i<ss.length; i++)
                     {
                         System.out.println( ss[i ]);
                     }
                    }
                    catch (Exception ex)
                    {
                         ex.printStackTrace();
                    }
            }

            public static void DeleteOldStatusExchangeFile(String pDirName)
            {
              File ff = new File(pDirName);
              File tempff[] = ff.listFiles();
              if ( tempff != null )
              {
                for (int i=0; i<tempff.length;i++)
                {
                   String fname = tempff[i].getName().toUpperCase();
                   if ( fname.startsWith("F") || fname.startsWith("N") )
                   {
                         tempff[i].delete();
                         //System.out.println( "File is "+ fname + " is deleted.");
                   }
                }
              }
            }

            /**
             * copy the content of a file into a specific directory
             * @param pFileInputStream, SourceFile
             * @param pTargetFileName, TargetFile
             * @return
             */
            public static boolean InstallFileToTarget(InputStream pFileInputStream, String pTargetFileName )
            {
                    byte buffer[] = new byte[500*1024];  // 500 KB
                    FileOutputStream fos = null;
                    try
                    {
                            fos = new FileOutputStream(pTargetFileName);
                    }
                    catch (Exception ex)
                    {
                             ex.printStackTrace();
                             return false;
                    }

                    int k=0;
                    do
                    {
                            try
                            {
                                k = pFileInputStream.read( buffer );
                                if ( k>0 )
                                {
                                   fos.write(buffer,0,k);
                                   System.out.print("x");
                                }
                            }
                            catch (Exception ex)
                            {
                        System.out.println();
                                    ex.printStackTrace();
                                    return false;
                            }
                    }
                    while ( k > 0 );
            System.out.println();
                    return true;
            }

            public static void displayInternetAdress( NetworkInterface pNetIF )
            {
                    Enumeration ipads = pNetIF.getInetAddresses();
                    int i=0;
                    while ( ipads.hasMoreElements()  )
                    {
                            InetAddress id = ( InetAddress ) ipads.nextElement();
                            System.out.println( i + ". IP=" + id.getHostAddress() );
                            i = i + 1;
                    }
            }

            public static String[] checkIPAdress()
            {
                    try
                    {
                Enumeration en = NetworkInterface.getNetworkInterfaces();
                        while ( en.hasMoreElements() )
                        {
                                NetworkInterface netif = (NetworkInterface)en.nextElement();

                                System.out.println( "Network Interface: " + netif.getDisplayName() );
                                displayInternetAdress( netif );
                        }
                        return null;
                    }
                    catch (Exception ex)
                    {
                            return null;
                    }
            }


            public static String getCurrentAbsoluteDirectory()
            {

              return getAbsoluteDirectory(".");

              /*
               File dir1 = new File (".");
               String CurrentDir =  ".";
               try
               {
                  CurrentDir = dir1.getCanonicalPath();
               }
               catch(Exception ex)
               {
                  ex.printStackTrace();
               }
               return  CurrentDir;

              */
             }

             public static String getAbsoluteDirectory(String pDir)
             {
                File dir1 = new File ( pDir );
                String CurrentDir =  null;
                try
                {
                   CurrentDir = dir1.getCanonicalPath();
                }
                catch(Exception ex)
                {
                   ex.printStackTrace();
                }
                return  CurrentDir;
              }


             public static String getUnixFileName(String pFileName )
             {
                 String ss = pFileName;
                 ss = ss.replace('\\', '/');
                 return ss;
             }

             /**
              * selftest
              * @param args
              */

             public static void main(String[] args)
             {

                     System.out.println( "AAA"+ getCurrentAbsoluteDirectory() );

                     System.out.println( getAbsoluteDirectory("C:/fasmdev-new") );

                     System.out.println( getAbsoluteDirectory("reports") );

                     checkIPAdress();

                     displayFilesOfDirectory(".", ".");

                     //System.out.println("display lib ");

                     //displayFilesOfDirectory(".", "lib");

                     createDirectory("reports/2007_03_10test1.xml");

                     try
                     {
                       FileInputStream fins = new FileInputStream("D:/BEAWLS910/weblogic91/server/lib/weblogic.jar");
                       boolean dd = InstallFileToTarget( fins, "E:/chenfenghui/lib/test.jar" );
                       System.out.println("Installation is successful.");
                     }
                     catch (Exception ex)
                     {
                             ex.printStackTrace();
                     }
             }

}


