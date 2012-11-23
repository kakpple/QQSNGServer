package nanoFuntas.qqsngServer;

import org.json.simple.JSONObject;
/**
 * This DataHandler class receives data from servlet and handle data and finally returns data to servlet 
 */
public class DataHandler {
	private static boolean DEBUG = true;
	private static String TAG = "DataHandler";
	
	private static final String REQ_TYPE = "REQ_TYPE";
	private static final String REQ_SELF_INFO = "REQ_SELF_INFO";
	private static final String SELF_ID = "SELF_ID";
	private static final String RSP_TYPE = "RSP_TYPE";
	private static final String RSP_SELF_INFO = "RSP_SELF_INFO";
	private static final String HEART = "HEART";
	private static final String SCORE = "SCORE";
	private static final String GOLD = "GOLD";
	private static final String RSP_FRIENDS_INFO = "RSP_FRIENDS_INFO";
	private static final String FRIEND_ID = "FRIEND_ID";
	private static final String REQ_SCORE_UPDATE = "REQ_SCORE_UPDATE";
	private static final String RSP_SOCRE_UPDATE = "RSP_SOCRE_UPDATE";
	private static final String STAT_CODE = "STAT_CODE";
	private static final String STAT_CODE_OK = "STAT_CODE_OK";
	
	/**
	 * This handleData function handles data received from servlet and returns result data to servlet
	 * 
	 * @param jsonReq, JSONObject received from servlet
	 * @return JSONObject result data to servlet
	 */
	public static JSONObject handleData(JSONObject jsonReq) {
		String mReqType = (String) jsonReq.get(DataHandler.REQ_TYPE);
		if(DEBUG) System.out.println(TAG + ", " + mReqType);	
		if(DEBUG) System.out.println(TAG + ", " + jsonReq.toString());	
		
		// set JSON parameter to send
		JSONObject jsonRsp = new JSONObject();
			
		if (mReqType.equals(DataHandler.REQ_SELF_INFO)){
			String selfID = (String) jsonReq.get(DataHandler.SELF_ID);			
			if(DEBUG) System.out.println(TAG + ", self ID: " + selfID );

			// set jsonRsp header
			jsonRsp.put(DataHandler.RSP_TYPE, DataHandler.RSP_SELF_INFO);
			
			jsonRsp.put(DataHandler.SELF_ID, selfID);
			jsonRsp.put(DataHandler.HEART, 5);
			jsonRsp.put(DataHandler.SCORE, 6);
			jsonRsp.put(DataHandler.GOLD, 6);
			
			//sendJsonRsp(response, jsonRsp);
			
		} else if (mReqType.equals("REQ_FRIENDS_INFO")){
			// Number of friends is JSON size - 1, here -1 is performed because size of Header(REP_TYPE) which is 1 need to be subtracted
			int NumOfFriends = jsonReq.size() - 1;
			JSONObject[] jsonFriend = new JSONObject[NumOfFriends + 1];
			for(int i = 0; i <= NumOfFriends; i++){
				jsonFriend[i] = new JSONObject();
			}
			
			// set jsonRsp header
			jsonRsp.put(DataHandler.RSP_TYPE, DataHandler.RSP_FRIENDS_INFO);
			
			for(int i = 1; i <= NumOfFriends; i++){
				String friendId = (String) jsonReq.get(Integer.toString(i));
				if(DEBUG) System.out.println(TAG + ", friend Id: " + friendId );
				// TODO friend id process
				jsonFriend[i].put(DataHandler.FRIEND_ID, friendId);
				jsonFriend[i].put(DataHandler.HEART, 10);
				jsonFriend[i].put(DataHandler.SCORE, 11);
				jsonFriend[i].put(DataHandler.GOLD, 12);
				
				jsonRsp.put(i, jsonFriend[i]);				
			}
			
			//sendJsonRsp(response, jsonRsp);
		} else if (mReqType.equals(DataHandler.REQ_SCORE_UPDATE)){
			double score = (Double) jsonReq.get(DataHandler.SCORE);			
			if(DEBUG) System.out.println(TAG + ", score: " + Double.toString(score) );
			
			jsonRsp.put(DataHandler.RSP_TYPE, DataHandler.RSP_SOCRE_UPDATE);
			jsonRsp.put(DataHandler.STAT_CODE, DataHandler.STAT_CODE_OK);
			
			//sendJsonRsp(response, jsonRsp);
		}
		return jsonRsp;
		
		/*
		
		DatabaseService mDatabaseService = new DatabaseService();		

		if(mReqType.equals("FETCH_SELF_INFO")){
			String selfID = (String) jsonReq.get("SELF_ID");
			if(DEBUG) System.out.println(TAG + ", selfID =  " + selfID);	
			
			if( mDatabaseService.isUserRegistered(selfID) ){
				if(DEBUG) System.out.println(TAG + ": isUserRegistered == true");
				jsonRsp.put("RSP_TYPE", "USER_REGISTERED");
				sendJsonRsp(response, jsonRsp);
			} else {
				if(DEBUG) System.out.println(TAG + ": isUserRegistered == false");
				mDatabaseService.registerUser(selfID);	
				jsonRsp.put("RSP_TYPE", "USER_NOT_REGISTERED");
				sendJsonRsp(response, jsonRsp);
			};
		}
		*/
		
	}

}
