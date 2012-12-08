package nanoFuntas.qqsngServer.bll;

import nanoFuntas.qqsngServer.IProtocol;
import nanoFuntas.qqsngServer.dal.DBHelper;
import nanoFuntas.qqsngServer.dal.UserInfo;

import org.json.simple.JSONObject;

/**
 * This DataHandler class receives data from servlet and handle data and finally
 * returns data to servlet
 */


// Rex TODO: Currently this class handles all kind of requests, but I think we
// should make seperate Servlets for every kind of request , such as
// GetSelfInfo,GetFrindList,Get...., UpdateScore.. this is easier to maintain.
public class DataHandler {
	private static boolean DEBUG = true;
	private static String TAG = "DataHandler";

	/**
	 * This handleData function handles data received from servlet and returns
	 * result data to servlet
	 * 
	 * @param jsonReq
	 *            , JSONObject received from servlet
	 * @return JSONObject result data to servlet
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject handleData(JSONObject jsonReq) {
		String mReqType = (String) jsonReq.get(IProtocol.REQ_TYPE);
		if (DEBUG)
			System.out.println(TAG + ", " + mReqType);
		if (DEBUG)
			System.out.println(TAG + ", " + jsonReq.toString());

		// set JSON parameter to send
		JSONObject jsonRsp = new JSONObject();

		if (mReqType.equals(IProtocol.REQ_SELF_INFO)) {
			String selfID = (String) jsonReq.get(IProtocol.SELF_ID);
			if (DEBUG)
				System.out.println(TAG + ", self ID: " + selfID);

			// set jsonRsp header
			jsonRsp.put(IProtocol.RSP_TYPE, IProtocol.RSP_SELF_INFO);

			jsonRsp.put(IProtocol.SELF_ID, selfID);
			jsonRsp.put(IProtocol.HEART, 5);
			jsonRsp.put(IProtocol.SCORE, 6);
			jsonRsp.put(IProtocol.GOLD, 6);

			// sendJsonRsp(response, jsonRsp);

		} else if (mReqType.equals("REQ_FRIENDS_INFO")) {
			// Number of friends is JSON size - 1, here -1 is performed because
			// size of Header(REP_TYPE) which is 1 need to be subtracted
			UserInfo[] uis = DBHelper.getFriendsList("");
			int NumOfFriends = uis.length;
			// int NumOfFriends = jsonReq.size() - 1;
			JSONObject[] jsonFriend = new JSONObject[NumOfFriends];
			for (int i = 0; i <= NumOfFriends; i++) {
				jsonFriend[i] = new JSONObject();
			}

			// set jsonRsp header
			jsonRsp.put(IProtocol.RSP_TYPE, IProtocol.RSP_FRIENDS_INFO);

			for (int i = 0; i < NumOfFriends; i++) {
				String friendId = (String) jsonReq.get(Integer.toString(i));
				if (DEBUG)
					System.out.println(TAG + ", friend Id: " + friendId);
				// TODO friend id process
				jsonFriend[i].put(IProtocol.FRIEND_ID, uis[i].id);
				jsonFriend[i].put(IProtocol.HEART, uis[i].heart);
				jsonFriend[i].put(IProtocol.SCORE, uis[i].score);
				jsonFriend[i].put(IProtocol.GOLD, uis[i].gold);

				jsonRsp.put(i, jsonFriend[i]);
			}

			// sendJsonRsp(response, jsonRsp);
		} else if (mReqType.equals(IProtocol.REQ_SCORE_UPDATE)) {
			double score = (Double) jsonReq.get(IProtocol.SCORE);
			if (DEBUG)
				System.out.println(TAG + ", score: " + Double.toString(score));

			jsonRsp.put(IProtocol.RSP_TYPE, IProtocol.RSP_SOCRE_UPDATE);
			jsonRsp.put(IProtocol.STAT_CODE, IProtocol.STAT_CODE_OK);
		} else {
			jsonRsp.put("Error", "UnknownRequest");// indicate error.
		}

		return jsonRsp;

		/*
		 * 
		 * DatabaseService mDatabaseService = new DatabaseService();
		 * 
		 * if(mReqType.equals("FETCH_SELF_INFO")){ String selfID = (String)
		 * jsonReq.get("SELF_ID"); if(DEBUG) System.out.println(TAG +
		 * ", selfID =  " + selfID);
		 * 
		 * if( mDatabaseService.isUserRegistered(selfID) ){ if(DEBUG)
		 * System.out.println(TAG + ": isUserRegistered == true");
		 * jsonRsp.put("RSP_TYPE", "USER_REGISTERED"); sendJsonRsp(response,
		 * jsonRsp); } else { if(DEBUG) System.out.println(TAG +
		 * ": isUserRegistered == false");
		 * mDatabaseService.registerUser(selfID); jsonRsp.put("RSP_TYPE",
		 * "USER_NOT_REGISTERED"); sendJsonRsp(response, jsonRsp); }; }
		 */

	}

}
