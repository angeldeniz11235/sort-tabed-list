package sort_tabed_list;

import java.io.*;
import java.net.URL;
import java.util.*;

public class sort {
	public static ArrayList<String> readInFile(String filename) {
		ArrayList<String> lineArray = new ArrayList<String>();
		try {
			File file = new File(filename);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st;
			while ((st = br.readLine()) != null) {
				lineArray.add(st);
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return lineArray;
	}

	public static String getSortedStr(List<String> inList) {
		SortedSet<String> returnSortedSet = new TreeSet<String>();
		ArrayList<String> tmpSet = new ArrayList<String>();
		Iterator<String> strIter = inList.iterator();
		while (strIter.hasNext()) {
			String ts = strIter.next();
			if (!ts.isBlank()) {
				tmpSet.add(ts);
			} else {
				returnSortedSet.add(sortStr(tmpSet));
				tmpSet.clear();
			}
		}
		StringBuffer sb = new StringBuffer();
		for (String string : returnSortedSet) {
			sb.append(string + "\n");
		}
		return sb.toString();
	}

	private static String sortStr(ArrayList<String> inputSet) {
		ArrayList<String> returnArr = new ArrayList<String>();
		Iterator<String> strIt = inputSet.iterator();
		innerList innList = new innerList();
		SortedSet<sort.innerList> sortedNestedList = new TreeSet<sort.innerList>();
		while (strIt.hasNext()) {
			String string = strIt.next();
			long tabCount = string.chars().filter(ch -> ch == '\t').count();
			if (tabCount == 0) {
				returnArr.add(string);
			} else if (tabCount == 1) {
				if (!innList.isEmpty())
					sortedNestedList.add(innList);
				innList = new innerList();
				innList.setCategory(string);
			} else if (tabCount == 2) {
				innList.getSortedSet().add(string);
			}
			if (!strIt.hasNext()) {
				if (!innList.isEmpty())
					sortedNestedList.add(innList);
				for (innerList innerList : sortedNestedList) {
					returnArr.add(innerList.getCategory());
					returnArr.addAll(innerList.getSortedSet());
				}
				continue;
			}
		}

		StringBuffer sb = new StringBuffer();
		for (String string : returnArr) {
			sb.append(string + "\n");
		}

		return sb.toString();
	}

	public static void writeToFile(String str, String outFileName) {
		try {
			FileWriter fWriter = new FileWriter(System.getProperty("user.dir") + outFileName);
			fWriter.write(str);
			fWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws Exception {
		try {
			URL url = sort.class.getResource(args[0]);
			ArrayList<String> strList = readInFile(url.getPath());
			String sortedStr = getSortedStr(strList);
			System.out.print(sortedStr);
			if (args.length > 1) {
				writeToFile(sortedStr, args[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class innerList implements Comparable<innerList> {

		String category;
		SortedSet<String> sortedSet;

		public innerList() {
			category = "";
			sortedSet = new TreeSet<String>();
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public SortedSet<String> getSortedSet() {
			return sortedSet;
		}

		public void setSortedSet(SortedSet<String> sortedSet) {
			this.sortedSet = sortedSet;
		}

		public boolean isEmpty() {
			if (category.isEmpty()) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public int compareTo(innerList arg0) {
			return this.getCategory().compareTo(arg0.getCategory());
		}

	}

}