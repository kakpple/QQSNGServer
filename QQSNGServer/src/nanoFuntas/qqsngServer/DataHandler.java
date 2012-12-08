package nanoFuntas.qqsngServer;

import org.json.simple.JSONObject;
/**
 * This DataHandler class receives data from servlet and handle data and finally returns data to servlet 
 */
public class DataHandler  {
	private static boolean DEBUG = true;
	private static String TAG = "DataHandler";
	
	
	/**
	 * This handleData function handles data received from servlet and returns result data to servlet
	 * 
	 * @param jsonReq, JSONObject received from servlet
	 * @return JSONObject result data to servlet
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject handleData(JSONObject jsonReq) {
		String mReqType = (String) jsonReq.get(IProtocol.REQ_TYPE);
		if(DEBUG) System.out.println(TAG + ", " + mReqType);	
		if(DEBUG) System.out.println(TAG + ", " + jsonReq.toString());	
	
		// set JSON parameter to send
		JSONObject jsonRsp = new JSONObject();
			
		if (mReqType.equals(IProtocol.REQ_SELF_INFO)){
			String selfID = (String) jsonReq.get(IProtocol.SELF_ID);			
			if(DEBUG) System.out.println(TAG + ", self ID: " + selfID );

			// set jsonRsp header
			jsonRsp.put(IProtocol.RSP_TYPE, IProtocol.RSP_SELF_INFO);
			
			jsonRsp.put(IProtocol.SELF_ID, selfID);
			jsonRsp.put(IProtocol.HEART, 5);
			jsonRsp.put(IProtocol.SCORE, 6);
			jsonRsp.put(IProtocol.GOLD, 6);
			
			//sendJsonRsp(response, jsonRsp);
			
		} else if (mReqType.equals("REQ_FRIENDS_INFO")){
			// Number of friends is JSON size - 1, here -1 is performed because size of Header(REP_TYPE) which is 1 need to be subtracted
			int NumOfFriends = jsonReq.size() - 1;
			JSONObject[] jsonFriend = new JSONObject[NumOfFriends + 1];
			for(int i = 0; i <= NumOfFriends; i++){
				jsonFriend[i] = new JSONObject();
			}
			
			// set jsonRsp header
			jsonRsp.put(IProtocol.RSP_TYPE, IProtocol.RSP_FRIENDS_INFO);
			
			for(int i = 1; i <= NumOfFriends; i++){
				String friendId = (String) jsonReq.get(Integer.toString(i));
				if(DEBUG) System.out.println(TAG + ", friend Id: " + friendId );
				// TODO friend id process
				jsonFriend[i].put(IProtocol.FRIEND_ID, friendId);
				jsonFriend[i].put(IProtocol.HEART, 10);
				jsonFriend[i].put(IProtocol.SCORE, 11);
				jsonFriend[i].put(IProtocol.GOLD, 12);
				
				jsonRsp.put(i, jsonFriend[i]);				
			}
			
			//sendJsonRsp(response, jsonRsp);
		} else if (mReqType.equals(IProtocol.REQ_SCORE_UPDATE)){
			double score = (Double) jsonReq.get(IProtocol.SCORE);			
			if(DEBUG) System.out.println(TAG + ", score: " + Double.toString(score) );
			
			jsonRsp.put(IProtocol.RSP_TYPE, IProtocol.RSP_SOCRE_UPDATE);
			jsonRsp.put(IProtocol.STAT_CODE, IProtocol.STAT_CODE_OK);
			
			//sendJsonRsp(response, jsonRsp);
		}
		else{
			jsonRsp.put("Error", "UnknownRequest");
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
