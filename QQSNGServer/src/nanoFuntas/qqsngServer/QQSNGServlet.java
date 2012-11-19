package nanoFuntas.qqsngServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Servlet implementation class QQSNGServlet
 */
@WebServlet("/QQSNGServlet")
public class QQSNGServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private boolean DEBUG = true;
	private String TAG = "QQSNGServlet";
    /**
     * Default constructor. 
     */
    public QQSNGServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();	
		out.write("1");
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(DEBUG) System.out.println(TAG + ": doPost()");						
				
		// get JSON parameters
		JSONObject jsonReq = getJsonReq(request);
		String mReqType = (String) jsonReq.get("REQ_TYPE");
		if(DEBUG) System.out.println(TAG + ", " + mReqType);	
		
		// set JSON parameter to send
		JSONObject jsonRsp = new JSONObject();
			
		if(mReqType.equals("REQ_SELF_INFO")){
			String selfID = (String) jsonReq.get("SELF_ID");			
			jsonRsp.put("RSP_TYPE", "RSP_SELF_INFO");
			jsonRsp.put("SELF_ID", selfID);
			jsonRsp.put("HEART", 5);
			jsonRsp.put("SCORE", 345);
			jsonRsp.put("GOLD", 12);
			
			sendJsonRsp(response, jsonRsp);
		}
		
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
	
	/*
	 * Function sendStrRsp sends String data to client as response
	 * 
	 * @response this parameter is passed from HttpServletResponse response in function doPost()
	 * @strToSend String data to be sent to client
	 */
	private void sendStrRsp(HttpServletResponse response, String strToSend) {
		if(DEBUG) System.out.println(TAG + ": sendStrRsp()");						
		
		OutputStream outStrm = null;
		ObjectOutputStream objOutStrm = null;
		
		// write date to be sent to client
		try{
			outStrm = response.getOutputStream();
			objOutStrm = new ObjectOutputStream(outStrm);
			objOutStrm.writeObject(strToSend);
			objOutStrm.flush();
		} catch (IOException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			// release resources
			try{
				if(outStrm != null) outStrm.close();
				if(objOutStrm != null) objOutStrm.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	/*
	 * Function sendJsonRsp sends JSONObject data to client and wraps function sendStrRsp.
	 * 
	 * @response, this parameter is passed from HttpServletResponse response in function doPost()
	 * @jsonToSend, JSONObject data to be sent to client
	 */
	private void sendJsonRsp(HttpServletResponse response, JSONObject jsonToSend) {
		if(DEBUG) System.out.println(TAG + ": sendJsonRsp()");						
		
		String jsonStr = null;
		jsonStr = jsonToSend.toString();
		sendStrRsp(response, jsonStr);
	}	
	
	/*
	 * Function getStrReq extracts String data from request(HttpServletRequest request) parameter in doPost function, and return it.
	 * 
	 * @request, this parameter is passed from HttpServletRequest request in function doPost()
	 * @return, return String extracted from client request
	 */
	private String getStrReq(HttpServletRequest request) {
		if(DEBUG) System.out.println(TAG + ": getStrReq()");						
		
		String strReq = null;		
		InputStream inStrm = null;
		ObjectInputStream objInStrm = null;
		
		// get date from client
		try {
			inStrm = request.getInputStream();
			objInStrm = new ObjectInputStream(inStrm);
			strReq = (String) objInStrm.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){			
			e.printStackTrace();
		} finally {
			// release resources
			try{
				if(inStrm != null) inStrm.close();
				if(objInStrm != null) objInStrm.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}

		return strReq;
	}
	
	/*
	 * Function getJsonReq extracts JSONObject data from request(HttpServletRequest request) parameter in doPost function, and return it.
	 * Function getJsonReq wraps function getStrReq.
	 * 
	 * @request, this parameter is passed from HttpServletRequest request in function doPost()
	 * @return, return JSONObject extracted from client request
	 */
	private JSONObject getJsonReq(HttpServletRequest request){
		if(DEBUG) System.out.println(TAG + ": getJsonReq()");						
		
		String strReq = null;
		JSONObject jsonReq = null;
		strReq = getStrReq(request);
		jsonReq = (JSONObject) JSONValue.parse(strReq);
		
		return jsonReq;
	}
}
