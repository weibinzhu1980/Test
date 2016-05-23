
import java.io.*;
import java.net.*;

public class TestFuc {
	
	public enum MatchType
	{
		ExactMatch,
		PhraseMatch,
		BroadMatch
	}
	
	public static void main (String[] args)
	{
		//TestDownloadHTML();
		try
		{
			String url = URLEncoder.encode("apple iphone+", "UTF-8");
			System.out.println(url);
		}
		catch (Exception e)
		{
			
		}
	}
	
	public static void TestDownloadHTML()
	{
		String html = DownloadHTMLPage("http://cn.bing.com/search?q=%e5%be%ae%e8%bd%af%e6%ad%a3%e5%bc%8f%e6%8e%a8%e9%80%81win+10&qs=PN&sk=HS3&sc=8-0&sp=4&cvid=0b50e058a791454f85ea0a33144ac1bb&FORM=QBLH");
		System.out.println(html);
		
		OutputStreamWriter writer = null;
		
		try
		{
			writer = new OutputStreamWriter(new FileOutputStream("C:\\Learning\\a.html"),"UTF-8");
			writer.write(html);
		}
		catch (IOException e)
		{
			System.out.println("Error when writing file.");
		}
		finally
		{
			try
			{
				if (writer != null)
					writer.close();
			}
			catch (IOException e) {}
		}
	}
	
	public static void TestGetMatchType()
	{
		//Exact Match
		String query;
		String keyword;
		MatchType matchType;
		
		query = "Credit card";
		keyword = "credit card";
		matchType = GetMatchType (query, keyword);
		System.out.println(matchType);
		
		//Phrase Match
		query = "Credit card application";
		keyword = "credit card";
		
		matchType = GetMatchType (query, keyword);
		System.out.println(matchType);
		
		//Broad Match
		query = "XCredit card application";
		keyword = "credit card";
		
		matchType = GetMatchType (query, keyword);
		System.out.println(matchType);
		
		query = "Credit card";
		keyword = "credit card application";
		
		matchType = GetMatchType (query, keyword);
		System.out.println(matchType);
		
		//MISC
		query = "  ";
		keyword = " ";
		
		matchType = GetMatchType (query, keyword);
		System.out.println(matchType);
		
		
		query = "sfjkdeafsl;jfksECC";
		keyword = "XXXXDsfjsfo^KLDfdjsfl";
		
		matchType = GetMatchType (query, keyword);
		System.out.println(matchType);
		
		query = "XB";
		keyword = "X";
		
		matchType = GetMatchType (query, keyword);
		System.out.println(matchType);
		
		query = "X";
		keyword = "XB";
		
		matchType = GetMatchType (query, keyword);
		System.out.println(matchType);
		
		query = "X B";
		keyword = "B";
		
		matchType = GetMatchType (query, keyword);
		System.out.println(matchType);
		
		query = "A keyword phrase";
		keyword = "key";
		
		matchType = GetMatchType (query, keyword);
		System.out.println(matchType);
		
		query = "A keyword";
		keyword = "key";
		
		matchType = GetMatchType (query, keyword);
		System.out.println(matchType);
		
		query = "A keyword";
		keyword = "keyword";
		
		matchType = GetMatchType (query, keyword);
		System.out.println(matchType);		
	}
	
	public static MatchType GetMatchType(String query, String keyword)
	{
		String normalizedQuery = Normalize(query);
		String normalizedKeyword = Normalize(keyword);
		
		if (normalizedQuery.equals(normalizedKeyword))
		{
			return MatchType.ExactMatch;
		}
		else 
		{
			int pos = normalizedQuery.indexOf(normalizedKeyword);
			int qLen = normalizedQuery.length();
			int kLen = normalizedKeyword.length();
			//Match the first character
			if (pos == 0 && normalizedQuery.charAt(kLen) == ' ')
			{
				return MatchType.PhraseMatch;
			}
			//Match in the middle, check the next character is space or reach the string end
			else if ((pos > 0 && normalizedQuery.charAt(pos - 1) == ' '))
			{
				if (
				    (qLen == pos + kLen) || 
					(qLen > pos + kLen && normalizedQuery.charAt(pos + kLen) == ' ')
				   )
				{
					return MatchType.PhraseMatch;
				}
				else
				{
					return MatchType.BroadMatch;
				}
			}
			else
			{
				return MatchType.BroadMatch;
			}
		}
	}
	
	public static String Normalize(String str)
	{
		//ToLower
		str = str.toLowerCase();
		
		//Trim
		str = str.trim();
		
		//Remove special characters
		String charToRemove = "`~!@#$%^*()-_+=[]{}\\|,<>?/;:'\"";
		StringBuffer strBuf = new StringBuffer();
		
		int length = str.length();
		int strBufLen = 0;
		//set an initial value <> ' '
		char lastCh = 'a';
		
		for (int i = 0; i < length; i++)
		{
			char ch = str.charAt(i);
			
			
			if (strBufLen >= 1)
			{
				lastCh = strBuf.charAt(strBufLen - 1);
			}
			
			//Remove special character
			if (charToRemove.indexOf(ch) != -1)
			{
				if(lastCh != ' ')
				{
					strBuf.append(' ');
					strBufLen++;
				}
			}
			else if (ch != ' ' || (ch == ' ' && lastCh != ' '))
			{
				strBuf.append(ch);
				strBufLen++;
			}
		}
		
		return strBuf.toString();
	}

	public static String DownloadHTMLPage(String url)
	{
	      //-----------------------------------------------------//
	      //  Step 1:  Start creating a few objects we'll need.
	      //-----------------------------------------------------//
	 
	      URL u;
	      InputStream is = null;
	      BufferedReader bReader;
	      String s;
	      
	      StringBuffer strBuf = new StringBuffer();
	 
	      try {
	 
	         //------------------------------------------------------------//
	         // Step 2:  Create the URL.                                   //
	         //------------------------------------------------------------//
	         // Note: Put your real URL here, or better yet, read it as a  //
	         // command-line arg, or read it from a file.                  //
	         //------------------------------------------------------------//
	 
	         u = new URL(url);
	 
	         //----------------------------------------------//
	         // Step 3:  Open an input stream from the url.  //
	         //----------------------------------------------//
	 
	         is = u.openStream();         // throws an IOException
	 
	         //-------------------------------------------------------------//
	         // Step 4:                                                     //
	         //-------------------------------------------------------------//
	         // Convert the InputStream to a buffered DataInputStream.      //
	         // Buffering the stream makes the reading faster; the          //
	         // readLine() method of the DataInputStream makes the reading  //
	         // easier.                                                     //
	         //-------------------------------------------------------------//
	 
	         bReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	 
	         //------------------------------------------------------------//
	         // Step 5:                                                    //
	         //------------------------------------------------------------//
	         // Now just read each record of the input stream, and print   //
	         // it out.  Note that it's assumed that this problem is run   //
	         // from a command-line, not from an application or applet.    //
	         //------------------------------------------------------------//
	         
	         while ((s = bReader.readLine()) != null) {
	        	 strBuf.append(s);
	        	 strBuf.append("\n");
	         }
	 
	      } catch (MalformedURLException mue) {
	 
	         return null;
	 
	      } catch (IOException ioe) {
	 
	         return null;
	 
	      } finally {
	 
	         //---------------------------------//
	         // Step 6:  Close the InputStream  //
	         //---------------------------------//
	 
	         try {
	            is.close();
	         } catch (IOException ioe) {
	            // just going to ignore this one
	        	 return null;
	         }
	 
	      } // end of 'finally' clause
	      
	      return strBuf.toString();
	 
	   }
		
	}
