package external;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.monkeylearn.ExtraParam;
import com.monkeylearn.MonkeyLearn;
import com.monkeylearn.MonkeyLearnException;
import com.monkeylearn.MonkeyLearnResponse;

public class MonkeyLearnClient {
	private static final String API_KEY = "6192db9cba3b5a9641e958cb096d5a015d944437";
	public static List<List<String>> extractKeywords(String[] text) {
		if (text == null || text.length == 0) {
			return new ArrayList<> ();
		}
		MonkeyLearn ml = new MonkeyLearn(API_KEY);
		ExtraParam[] extraParams = {new ExtraParam("max_keywords", "3")};
		MonkeyLearnResponse response;
		try {
			response = ml.extractors.extract("ex_YCya9nrn", text, extraParams);
			JSONArray resultArray = response.arrayResult;
			System.out.print(resultArray.toString());
			return getKeywords(resultArray);
		} catch (MonkeyLearnException e) {// it’s likely to have an exception
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	public static void main(String[] args) {
		String[] textList = {"Elon Musk has shared a photo of the spacesuit designed by SpaceX. This is the second image shared of the new design"
				+ " and the first to feature the spacesuit’s full-body look.",};
		List<List<String>> words = extractKeywords(textList);
		for (List<String> ws : words) {
			for (String w : ws) {
			//	System.out.print(w);
			}
		//	System.out.println();

		}
 	}
	private static List<List<String>> getKeywords(JSONArray mlResultArray) {
		List<List<String>> topKeywords =  new ArrayList<>();
		for (int i = 0; i < mlResultArray.size(); ++i) {
			List<String> keywords = new ArrayList<>();
			JSONArray keywordsArray = (JSONArray) mlResultArray.get(i);
			for (int j = 0; j < keywordsArray.size(); ++j) {
				JSONObject keywordObject = (JSONObject) keywordsArray.get(j);
				String keyword = (String) keywordObject.get("keyword");
				keywords.add(keyword);
			}
			topKeywords.add(keywords);
		}
		return topKeywords;
	}
}
