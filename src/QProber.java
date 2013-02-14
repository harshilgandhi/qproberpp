/***************************************************************************************
			SOME GENERAL INFORMATION
	
			(i)	we98fi/LYjeA82+BEq+2tl7oR8H6V+NUWAJ9E8IWD5k= --> Account Key
					
***************************************************************************************/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class QProber {

	private static boolean debug = false;
	private static String accountKey;
	private static double t_es;
	private static double t_ec;
	private static String hostURL;
	private static String[] rootQueries;
	private static String[] rootMap;
	private static String[] computersQueries;
	private static String[] computersMap;
	private static String[] healthQueries;
	private static String[] healthMap;
	private static String[] sportsQueries;
	private static String[] sportsMap;
	private static double[] totalRootDocs;
	private static double computers;
	private static double health;
	private static double sports;
	private static double[] totalComputersDocs;
	private static double hardware;
	private static double programming;
	private static double[] totalHealthDocs;
	private static double fitness;
	private static double diseases;
	private static double[] totalSportsDocs;
	private static double basketball;
	private static double soccer;
	private static double rootDatabaseSize;
	private static double computersDatabaseSize;
	private static double healthDatabaseSize;
	private static double sportsDatabaseSize;
	private static SortedMap<String, Set<String>> rootWords = new TreeMap<String, Set<String>>();
	private static SortedMap<String, Set<String>> computersWords = new TreeMap<String, Set<String>>();
	private static SortedMap<String, Set<String>> healthWords = new TreeMap<String, Set<String>>();
	private static SortedMap<String, Set<String>> sportsWords = new TreeMap<String, Set<String>>();
	private static Set<String> printSet = new HashSet<String>();
	private static List<Document> rootResultDocList = new ArrayList<Document>();
	private static List<Document> computersResultDocList = new ArrayList<Document>();
	private static List<Document> healthResultDocList = new ArrayList<Document>();
	private static List<Document> sportsResultDocList = new ArrayList<Document>();
	private static StringBuffer bingURL;
	private static Set<String> rootDocUrlSet = new HashSet<String>();
	private static Set<String> computersDocUrlSet = new HashSet<String>();
	private static Set<String> healthDocUrlSet = new HashSet<String>();
	private static Set<String> sportsDocUrlSet = new HashSet<String>();
	
	public static void main(String[] args) throws IOException {
		initialize();
		accountKey = args[0];
		t_es = Double.parseDouble(args[1]);
		t_ec = Double.parseDouble(args[2]);
		hostURL = args[3];
		System.out.println("Classifying...");
		computeRootTotal();
		QProber.log("*CHECKING ROOT*");
		printSet.add("Root");
		System.out.println("Specificity for category:Computers = "+(computers / rootDatabaseSize));
		System.out.println("Coverage for category:Computers = "+(computers));
		
		if(computers >= t_ec && (computers / rootDatabaseSize) >= t_es)
		{
			printSet.add("Computers");
			QProber.log("Computers C = "+computers+", S = "+(computers / rootDatabaseSize)+", |D| = "+rootDatabaseSize);
			computeComputersTotal();
			if(hardware >= t_ec && (computers / rootDatabaseSize) * (hardware / computersDatabaseSize) >= t_es)
			{
				printSet.add("Hardware");
				QProber.log("Hardware C = "+hardware+", S = "+(computers / rootDatabaseSize) * (hardware / computersDatabaseSize)+", |D| = "+computersDatabaseSize);
			}
			if(programming >= t_ec && (computers / rootDatabaseSize) * (programming / computersDatabaseSize) >= t_es)
			{
				printSet.add("Programming");
				QProber.log("Programming C = "+programming+", S = "+(computers / rootDatabaseSize) * (programming / computersDatabaseSize)+", |D| = "+computersDatabaseSize);
			}
			System.out.println("Specificity for category:Programming = "+(computers / rootDatabaseSize) * (programming / computersDatabaseSize));
			System.out.println("Coverage for category:programming = "+(programming));
			System.out.println("Specificity for category:Hardware = "+(computers / rootDatabaseSize) * (hardware / computersDatabaseSize));
			System.out.println("Coverage for category:Hardware = "+(hardware));
		}
		QProber.log("*CHECKING HEALTH*");
		System.out.println("Specificity for category:Health = "+(health / rootDatabaseSize));
		System.out.println("Coverage for category:Health = "+(health));
		if(health >= t_ec && (health /rootDatabaseSize) >= t_es)
		{
			printSet.add("Health");
			QProber.log("Health C = "+health+", S = "+(health / rootDatabaseSize)+", |D| = "+rootDatabaseSize);
			computeHealthTotal();
			if(fitness >= t_ec && (health / rootDatabaseSize) * (fitness / healthDatabaseSize) >= t_es)
			{
				printSet.add("Fitness");
				QProber.log("Fitness C = "+fitness+", S = "+(health / rootDatabaseSize) * (fitness / healthDatabaseSize)+", |D| = "+healthDatabaseSize);
			}
			if(diseases >= t_ec && (health / rootDatabaseSize) * (diseases / healthDatabaseSize) >= t_es)
			{
				printSet.add("Diseases");
				QProber.log("Diseases C = "+diseases+", S = "+(health / rootDatabaseSize) * (diseases / healthDatabaseSize)+", |D| = "+healthDatabaseSize);
			}
			System.out.println("Specificity for category:Fitness = "+(health / rootDatabaseSize) * (fitness / healthDatabaseSize));
			System.out.println("Coverage for category:Fitness = "+(fitness));
			System.out.println("Specificity for category:Diseases = "+(health / rootDatabaseSize) * (diseases / healthDatabaseSize));
			System.out.println("Coverage for category:Diseases = "+(diseases));
		}
		System.out.println("Specificity for category:Sports = "+(sports / rootDatabaseSize));
		System.out.println("Coverage for category:Sports = "+(sports));
		QProber.log("*CHECKING SPORTS*");
		if(sports >= t_ec && (sports / rootDatabaseSize) >= t_es)
		{
			printSet.add("Sports");
			QProber.log("Computers C = "+computers+", S = "+(computers / rootDatabaseSize)+", |D| = "+rootDatabaseSize);
			computeSportsTotal();
			if(basketball >= t_ec && (sports / rootDatabaseSize) * (basketball / sportsDatabaseSize) >= t_es)
			{
				printSet.add("Basketball");
				QProber.log("Basketball C = "+basketball+", S = "+(sports / rootDatabaseSize) * (basketball / sportsDatabaseSize)+", |D| = "+sportsDatabaseSize);
			}
			if(soccer >= t_ec && (sports / rootDatabaseSize) * (soccer / sportsDatabaseSize) >= t_es)
			{
				printSet.add("Soccer");
				QProber.log("Soccer C = "+soccer+", S = "+(sports / rootDatabaseSize) * (soccer / sportsDatabaseSize)+", |D| = "+sportsDatabaseSize);
			}
			System.out.println("Specificity for category:Basketball = "+(sports / rootDatabaseSize) * (basketball / sportsDatabaseSize));
			System.out.println("Coverage for category:Basketball = "+(basketball));
			System.out.println("Specificity for category:Soccer = "+(sports / rootDatabaseSize) * (soccer / sportsDatabaseSize));
			System.out.println("Coverage for category:Soccer = "+(soccer));
		}
		
		QProber.log("*T*H*E***E*N*D*");
		System.out.println("Classification:");
		if(printSet.size() == 1)
		{
			System.out.println("Root/");
		}
		if(printSet.contains("Computers") && !printSet.contains("Hardware") && !printSet.contains("Programming"))
			System.out.println("Root/Computers/");
		if(printSet.contains("Health") && !printSet.contains("Fitness") && !printSet.contains("Diseases"))
			System.out.println("Root/Health/");
		if(printSet.contains("Sports") && !printSet.contains("Basketball") && !printSet.contains("Soccer"))
			System.out.println("Root/Sports/");
		if(printSet.contains("Hardware"))
			System.out.println("Root/Computers/Hardware/");
		if(printSet.contains("Programming"))
			System.out.println("Root/Computers/Programming/");
		if(printSet.contains("Fitness"))
			System.out.println("Root/Health/Fitness/");
		if(printSet.contains("Diseases"))
			System.out.println("Root/Health/Diseases/");
		if(printSet.contains("Basketball"))
			System.out.println("Root/Sports/Basketball/");
		if(printSet.contains("Soccer"))
			System.out.println("Root/Sports/Soccer/");
		makeComputersContentSummary();
		addAll(computersWords);
		makeHealthContentSummary();
		addAll(healthWords);
		makeSportsContentSummary();
		addAll(sportsWords);
		makeRootContentSummary();
		System.out.println("End of Program, please check the files created.");
	}
	
	private static void computeRootTotal() throws IOException
	{
		QProber.log("*ROOT*");
		for(int i = 0; i < rootQueries.length; i ++)
		{
			bingURL = new StringBuffer("https://api.datamarket.azure.com/Data.ashx/Bing/SearchWeb/v1/Composite?Query=%27site:"+ hostURL +"%20");
			String[] arr = rootQueries[i].split(" ");
			for(int j = 0; j < arr.length; j ++)
			{
				bingURL.append(arr[j]);
				if((j + 1)<arr.length)
					bingURL.append("%20");
			}
			bingURL.append("%27&$top=4&$format=Atom");
			byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
			String accountKeyEnc = new String(accountKeyBytes);
			URL url = new URL(bingURL+"");
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
			InputStream inputStream = (InputStream) urlConnection.getContent();		
			byte[] contentRaw = new byte[urlConnection.getContentLength()];
			inputStream.read(contentRaw);
			String content = new String(contentRaw);
			int beginIndex = content.indexOf("<d:WebTotal m:type=");
			beginIndex += 31;
			int endIndex = content.indexOf("</d:WebTotal>");
			totalRootDocs[i] = Double.parseDouble(content.substring(beginIndex, endIndex));
			Document resultDoc = Jsoup.parse(content,"",Parser.xmlParser());
			rootResultDocList.add(resultDoc);
		}
		for(int i = 0; i < totalRootDocs.length; i ++)
		{
			if(rootMap[i].equals("Computers"))
			{
				computers += totalRootDocs[i];
			}
			if(rootMap[i].equals("Health"))
			{
				health += totalRootDocs[i];
			}
			if(rootMap[i].equals("Sports"))
			{
				sports += totalRootDocs[i];
			}
		}
		rootDatabaseSize = computers + health + sports;
	}
	
	private static void computeComputersTotal() throws IOException
	{
		QProber.log("*COMPUTERS*");
		for(int i = 0; i < computersQueries.length; i ++)
		{
			bingURL = new StringBuffer("https://api.datamarket.azure.com/Data.ashx/Bing/SearchWeb/v1/Composite?Query=%27site:"+ hostURL +"%20");
			String[] arr = computersQueries[i].split(" ");
			for(int j = 0; j < arr.length; j ++)
			{
				bingURL.append(arr[j]);
				if((j + 1)<arr.length)
					bingURL.append("%20");
			}
			bingURL.append("%27&$top=4&$format=Atom");
			
			byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
			String accountKeyEnc = new String(accountKeyBytes);
			URL url = new URL(bingURL+"");
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
			InputStream inputStream = (InputStream) urlConnection.getContent();		
			byte[] contentRaw = new byte[urlConnection.getContentLength()];
			inputStream.read(contentRaw);
			String content = new String(contentRaw);
			int beginIndex = content.indexOf("<d:WebTotal m:type=");
			beginIndex += 31;
			int endIndex = content.indexOf("</d:WebTotal>");
			totalComputersDocs[i] = Double.parseDouble(content.substring(beginIndex, endIndex));
			Document resultDoc = Jsoup.parse(content,"",Parser.xmlParser());
			computersResultDocList.add(resultDoc);
		}
		for(int i = 0; i < totalComputersDocs.length; i ++)
		{
			if(computersMap[i].equals("Hardware"))
			{
				hardware += totalComputersDocs[i];
			}
			if(computersMap[i].equals("Programming"))
			{
				programming += totalComputersDocs[i];
			}
		}
		computersDatabaseSize = hardware + programming;
	}
	
	private static void computeHealthTotal() throws IOException
	{
		QProber.log("*HEALTH*");
		for(int i = 0; i < healthQueries.length; i ++)
		{
			bingURL = new StringBuffer("https://api.datamarket.azure.com/Data.ashx/Bing/SearchWeb/v1/Composite?Query=%27site:"+ hostURL +"%20");
			String[] arr = healthQueries[i].split(" ");
			for(int j = 0; j < arr.length; j ++)
			{
				bingURL.append(arr[j]);
				if((j + 1)<arr.length)
					bingURL.append("%20");
			}
			bingURL.append("%27&$top=4&$format=Atom");
			byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
			String accountKeyEnc = new String(accountKeyBytes);
			URL url = new URL(bingURL+"");
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
			InputStream inputStream = (InputStream) urlConnection.getContent();		
			byte[] contentRaw = new byte[urlConnection.getContentLength()];
			inputStream.read(contentRaw);
			String content = new String(contentRaw);
			int beginIndex = content.indexOf("<d:WebTotal m:type=");
			beginIndex += 31;
			int endIndex = content.indexOf("</d:WebTotal>");
			totalHealthDocs[i] = Double.parseDouble(content.substring(beginIndex, endIndex));
			Document resultDoc = Jsoup.parse(content,"",Parser.xmlParser());
			healthResultDocList.add(resultDoc);
		}
		for(int i = 0; i < totalHealthDocs.length; i ++)
		{
			if(healthMap[i].equals("Fitness"))
			{
				fitness += totalHealthDocs[i];
			}
			if(healthMap[i].equals("Diseases"))
			{
				diseases += totalHealthDocs[i];
			}
		}
		healthDatabaseSize = fitness + diseases;
	}
	
	private static void computeSportsTotal() throws IOException
	{
		QProber.log("*SPORTS*");
		for(int i = 0; i < sportsQueries.length; i ++)
		{
			bingURL = new StringBuffer("https://api.datamarket.azure.com/Data.ashx/Bing/SearchWeb/v1/Composite?Query=%27site:"+ hostURL +"%20");
			String[] arr = sportsQueries[i].split(" ");
			for(int j = 0; j < arr.length; j ++)
			{
				bingURL.append(arr[j]);
				if((j + 1)<arr.length)
					bingURL.append("%20");
			}
			bingURL.append("%27&$top=4&$format=Atom");
			byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
			String accountKeyEnc = new String(accountKeyBytes);
			URL url = new URL(bingURL+"");
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
			InputStream inputStream = (InputStream) urlConnection.getContent();		
			byte[] contentRaw = new byte[urlConnection.getContentLength()];
			inputStream.read(contentRaw);
			String content = new String(contentRaw);
			int beginIndex = content.indexOf("<d:WebTotal m:type=");
			beginIndex += 31;
			int endIndex = content.indexOf("</d:WebTotal>");
			totalSportsDocs[i] = Double.parseDouble(content.substring(beginIndex, endIndex));
			Document resultDoc = Jsoup.parse(content,"",Parser.xmlParser());
			sportsResultDocList.add(resultDoc);
		}
		for(int i = 0; i < totalSportsDocs.length; i ++)
		{
			if(sportsMap[i].equals("Basketball"))
			{
				basketball += totalSportsDocs[i];
			}
			if(sportsMap[i].equals("Soccer"))
			{
				soccer += totalSportsDocs[i];
			}
		}
		sportsDatabaseSize = basketball + soccer;
	}
	
	static class CustomObject {
		
		private String key;
		private Set<String> values;
		public CustomObject(String key, Set<String> values) {
		    this.key = key;
		    this.values = values;
		}
		public String getKey() {
		    return key;
		}
		public Set<String> getValues() {
		    return values;
		}
	}
	
	private static void addAll(SortedMap<String,Set<String>> map)
	{
		Iterator<Map.Entry<String, Set<String>>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Set<String>> pairs = iterator.next();
			if (rootWords.containsKey(pairs.getKey())) {
				Set<String> nodeSet = pairs.getValue();
				rootWords.get(pairs.getKey()).addAll(nodeSet);
			} else {
				Set<String> idSet = new HashSet<String>();
				idSet.addAll(pairs.getValue());
				rootWords.put(pairs.getKey(), idSet);
			}
		}
	}
	
	private static void writeToFile(String category) throws IOException
	{
		File f = new File(category + "-" + hostURL + ".txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		Iterator<Map.Entry<String, Set<String>>> iterator = null;;
		if(category.equals("Root"))
			iterator = rootWords.entrySet().iterator();
		else if(category.equals("Computers"))
			iterator = computersWords.entrySet().iterator();
		else if(category.equals("Health"))
			iterator = healthWords.entrySet().iterator();
		else if(category.equals("Sports"))
			iterator = sportsWords.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, Set<String>> pairs = (Map.Entry<String, Set<String>>)iterator.next();
			if(!pairs.getKey().equals(""))
				bw.write(pairs.getKey().toString() + " # " + pairs.getValue().size() + "\n");
		}
		bw.close();
	}
	
	private static void makeRootContentSummary() throws IOException
	{
		int j = 0;
		String docId = "";
		String docUrl = "";
		for(int i = 0; i < rootResultDocList.size(); i ++)
		{
			Document resultDoc = rootResultDocList.get(i);
			for ( Element entryElements : resultDoc.select("entry"))
			{	
				if(j == 0)
				{
					j ++;
					continue;
				}
				
				if(j == 3) 
					break;
				Elements e = entryElements.getElementsByTag("content");
				docId=(e.first().children().first().children().get(0).text());
				docUrl = (e.first().children().first().children().get(3).text());
				rootDocUrlSet.add(docUrl);
				if(docUrl.equals(""))
					continue;				
				System.out.println("URL being tokenized: " + docUrl);
				Set<String> tokenizedDocs = GetWordsLynx.runLynx(docUrl);
				Iterator<String> iterator = tokenizedDocs.iterator();
				
				while(iterator.hasNext())
				{	
					String newWord = iterator.next();
					//if rootWords has no such word as current word
					if(!rootWords.containsKey(newWord))
					{
						Set<String> s = new HashSet<String>();
						s.add(docId);
						rootWords.put(newWord, s);
					}
					//if rootWords has such a word...
					else if(rootWords.containsKey(newWord))
					{
						rootWords.get(newWord).add(docId);
					}
				}
				j ++;
			}
		}
		writeToFile("Root");
	}
	private static void makeComputersContentSummary() throws IOException
	{
		int j = 0;
		String docId = "";
		String docUrl = "";
		// Extract the Document ID, Title, Description and URL of the 4 searched items
		for(int i = 0; i < computersResultDocList.size(); i ++)
		{
			Document resultDoc = computersResultDocList.get(i);
			for ( Element entryElements : resultDoc.select("entry"))
			{	
				if(j == 0)
				{
					j ++;
					continue;
				}
				Elements e = entryElements.getElementsByTag("content");
				docId=(e.first().children().first().children().get(0).text());
				docUrl = (e.first().children().first().children().get(3).text());
				computersDocUrlSet.add(docUrl);
				if(docUrl.equals(""))
					continue;
				System.out.println("URL being tokenized: " + docUrl);
				Set<String> tokenizedDocs = GetWordsLynx.runLynx(docUrl);
				Iterator<String> iterator = tokenizedDocs.iterator();
				
				while(iterator.hasNext())
				{	
					String newWord = iterator.next();
					//if rootWords has no such word as current word
					if(!computersWords.containsKey(newWord))
					{
						Set<String> s = new HashSet<String>();
						s.add(docId);
						computersWords.put(newWord, s);
					}
					//if rootWords has such a word...
					else if(computersWords.containsKey(newWord))
					{	
						computersWords.get(newWord).add(docId);
					}
				}
				j ++;
			}
		}
		if(computersResultDocList.size() > 0)
			writeToFile("Computers");
	}
	
	private static void makeHealthContentSummary() throws IOException
	{
		int j = 0;
		String docId = "";
		String docUrl = "";
		//	Extract the Document ID, Title, Description and URL of the 4 searched items
		for(int i = 0; i < healthResultDocList.size(); i ++)
		{
			Document resultDoc = healthResultDocList.get(i);
			for ( Element entryElements : resultDoc.select("entry"))
			{	
				if(j == 0)
				{
					j ++;
					continue;
				}
				Elements e = entryElements.getElementsByTag("content");
				docId=(e.first().children().first().children().get(0).text());
				docUrl = (e.first().children().first().children().get(3).text());
				healthDocUrlSet.add(docUrl);
				if(docUrl.equals(""))
					continue;
				System.out.println("URL being tokenized: " + docUrl);
				Set<String> tokenizedDocs = GetWordsLynx.runLynx(docUrl);
				Iterator<String> iterator = tokenizedDocs.iterator();
				while(iterator.hasNext())
				{	
					String newWord = iterator.next();
					//if rootWords has no such word as current word
					if(!healthWords.containsKey(newWord))
					{
						Set<String> s = new HashSet<String>();
						s.add(docId);
						healthWords.put(newWord, s);
					}
					//if rootWords has such a word...
					else if(healthWords.containsKey(newWord))
					{	
						healthWords.get(newWord).add(docId);
					}
				}
				j ++;
			}
		}
		if(healthResultDocList.size() > 0)
			writeToFile("Health");
	}
	
	private static void makeSportsContentSummary() throws IOException
	{
		int j = 0;
		String docId = "";
		String docUrl = "";
		//	Extract the Document ID, Title, Description and URL of the 4 searched items
		for(int i = 0; i < sportsResultDocList.size(); i ++)
		{
			Document resultDoc = sportsResultDocList.get(i);
			for ( Element entryElements : resultDoc.select("entry"))
			{	
				if(j == 0)
				{
					j ++;
					continue;
				}
				Elements e = entryElements.getElementsByTag("content");
				docId=(e.first().children().first().children().get(0).text());
				docUrl = (e.first().children().first().children().get(3).text());
				sportsDocUrlSet.add(docUrl);
				if(docUrl.equals(""))
					continue;
				System.out.println("URL being tokenized: " + docUrl);
				Set<String> tokenizedDocs = GetWordsLynx.runLynx(docUrl);
				Iterator<String> iterator = tokenizedDocs.iterator();
				while(iterator.hasNext())
				{	
					String newWord = iterator.next();
					//if rootWords has no such word as current word
					if(!sportsWords.containsKey(newWord))
					{
						Set<String> s = new HashSet<String>();
						s.add(docId);
						sportsWords.put(newWord, s);
					}
					//if rootWords has such a word...
					else if(sportsWords.containsKey(newWord))
					{	
						sportsWords.get(newWord).add(docId);
					}
				}
				j ++;
			}
		}
		if(sportsResultDocList.size() > 0)
			writeToFile("Sports");
	}
	
	private static void initialize() {
		rootQueries = new String[] {
			"cpu",
			"java",
			"module",
			"multimedia",
			"perl",
			"vb",
			"agp card",
			"application windows",
			"applet code",
			"array code",
			"audio file",
			"avi file",
			"bios",
			"buffer code",
			"bytes code",
			"shareware",
			"card drivers",
			"card graphics",
			"card pc",
			"pc windows",
			"acupuncture",
			"aerobic",
			"aerobics",
			"aids",
			"cancer",
			"cardiology",
			"cholesterol",
			"diabetes",
			"diet",
			"fitness",
			"hiv",
			"insulin",
			"nurse",
			"squats",
			"treadmill",
			"walkers",
			"calories fat",
			"carb carbs",
			"doctor health",
			"doctor medical",
			"eating fat",
			"fat muscle",
			"health medicine",
			"health nutritional",
			"hospital medical",
			"hospital patients",
			"medical patient",
			"medical treatment",
			"patients treatment",
			"laker",
			"ncaa",
			"pacers",
			"soccer",
			"teams",
			"wnba",
			"nba",
			"avg league",
			"avg nba",
			"ball league",
			"ball referee",
			"ball team",
			"blazers game",
			"championship team",
			"club league",
			"fans football",
			"game league"
		};
		rootMap = new String[] {
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Computers",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Health",
				"Sports",
				"Sports",
				"Sports",
				"Sports",
				"Sports",
				"Sports",
				"Sports",
				"Sports",
				"Sports",
				"Sports",
				"Sports",
				"Sports",
				"Sports",
				"Sports",
				"Sports",
				"Sports",
				"Sports"
		};
		computersQueries = new String[] {
			"bios",
			"motherboard",
			"board fsb",
			"board overclocking",
			"fsb overclocking",
			"bios controller ide",
			"cables drive floppy",
			"actionlistener",
			"algorithms",
			"alias",
			"alloc",
			"ansi",
			"api",
			"applet",
			"argument", 
			"array",
			"binary",
			"boolean",
			"borland",
			"char",
			"class",
			"code",
			"compile",
			"compiler",
			"component",
			"container",
			"controls",
			"cpan",
			"java",
			"perl"		
		};
		computersMap = new String[] {
			"Hardware",
			"Hardware",
			"Hardware",
			"Hardware",
			"Hardware",
			"Hardware",
			"Hardware",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming",
			"Programming"
		};
		healthQueries = new String[] {
			"aids",
			"cancer",
			"dental",
			"diabetes",
			"hiv",
			"cardiology",
			"aspirin cardiologist",
			"aspirin cholesterol",
			"blood heart",
			"blood insulin",
			"cholesterol doctor",
			"cholesterol lipitor",
			"heart surgery",
			"radiation treatment",
			"aerobic",
			"fat",
			"fitness",
			"walking",
			"workout",
			"acid diets",
			"bodybuilding protein",
			"calories protein",
			"calories weight",
			"challenge walk",
			"dairy milk",
			"eating protein",
			"eating weight",
			"exercise protein",
			"exercise weight"
		};
		healthMap = new String[] {
			"Diseases",
			"Diseases",
			"Diseases",
			"Diseases",
			"Diseases",
			"Diseases",
			"Diseases",
			"Diseases",
			"Diseases",
			"Diseases",
			"Diseases",
			"Diseases",
			"Diseases",
			"Diseases",
			"Fitness",
			"Fitness",
			"Fitness",
			"Fitness",
			"Fitness",
			"Fitness",
			"Fitness",
			"Fitness",
			"Fitness",
			"Fitness",
			"Fitness",
			"Fitness",
			"Fitness",
			"Fitness",
			"Fitness"
		};
		sportsQueries = new String[] {
			"uefa",
			"leeds",
			"bayern",
			"bundesliga",
			"premiership",
			"lazio",
			"mls",
			"hooliganism",
			"juventus",
			"liverpool",
			"fifa",
			"nba",
			"pacers",
			"kobe",
			"laker",
			"shaq",
			"blazers",
			"knicks",
			"sabonis",
			"shaquille",
			"laettner",
			"wnba",
			"rebounds",
			"dunks"
		};
		sportsMap = new String[] {
			"Soccer",
			"Soccer",
			"Soccer",
			"Soccer",
			"Soccer",
			"Soccer",
			"Soccer",
			"Soccer",
			"Soccer",
			"Soccer",
			"Soccer",
			"Basketball",
			"Basketball",
			"Basketball",
			"Basketball",
			"Basketball",
			"Basketball",
			"Basketball",
			"Basketball",
			"Basketball",
			"Basketball",
			"Basketball",
			"Basketball",
			"Basketball"
		};
		totalRootDocs = new double[rootQueries.length];
		totalComputersDocs = new double[computersQueries.length];
		totalHealthDocs = new double[healthQueries.length];
		totalSportsDocs = new double[sportsQueries.length];
	}

	private static void log(Object errCheck) {
		if(debug)
			System.err.println(errCheck.toString());
	}
}
