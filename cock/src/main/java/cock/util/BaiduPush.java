package cock.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushBroadcastMessageRequest;
import com.baidu.yun.channel.model.PushBroadcastMessageResponse;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;

public class BaiduPush {

	private BaiduChannelClient channelClient;
	
	private static String API_KEY;
	private static String SECRIT_KEY;
	
	List<String> tokens = new ArrayList<String>();
	List<String> pushUserIds = new ArrayList<String>();
	
//	int countRecord = 1;
//	boolean sendCount = false;
	
	static{
//		Map<String,String> configMap = 
//				ResourceUtil.getPropertie("mobileconfig/mobileconfig.properties");
//		API_KEY = configMap.get("andriod_api_key");
//		SECRIT_KEY = configMap.get("andriod_secrit_key");
		API_KEY = "mEwCrGdZTctkVj7MG2wbpQT1";
		SECRIT_KEY = "f9L1tdLyOiDSKbwPQfOwHLlwu4VbAEP9";
//		IPHONE_P12_PATH =  configMap.get("iphone_p12_path");
//		IPHONE_P12_PASSWORD = configMap.get("iphone_p12_password");
	}

	/**
	 * 构造函数
	 * 
	 * @param http_mehtod
	 *            请求方式
	 * @param secret_key
	 *            安全key
	 * @param api_key
	 *            应用key
	 */
	public BaiduPush(String api_key, String secret_key) {
		ChannelKeyPair pair = new ChannelKeyPair(api_key, secret_key);
		channelClient = new BaiduChannelClient(pair);
	}
	
	public static void main(String[] args) {
		System.out.println("------");
		BaiduPush baipush = new BaiduPush(API_KEY, SECRIT_KEY);
		Map<String, String> map = new HashMap<String, String>();
		map.put("test", "ttt");
		map.put("type", "ttt");
		map.toString();
		baipush.pushUnicastMessage("您有新的消息", "bbbbb", map, "816911264768932779");
	}

	//给指定用户发送通知
	public int pushUnicastMessage(String title, String message, Map<String, String> extraMap, String userId) {

		int pushSuccessAmount = 0;
		try {

			// 4. 创建请求类对象
			// 手机端的ChannelId， 手机端的UserId， 先用1111111111111代替，用户需替换为自己的
			PushUnicastMessageRequest request = new PushUnicastMessageRequest();
			request.setDeviceType(3); // device_type => 1: web 2: pc 3:android
										// 4:ios 5:wp
			// request.setChannelId(chnnelId);
			request.setUserId(userId);
			request.setMessageType(1);
			String msg = "";
			if(extraMap == null) {
				msg = String.format("{'title':'%s','description':'%s'}",title, message);
			} else {
				msg = String.format("{'title':'%s','description':'%s','custom_content':'%s'}",
						title, message, extraMap.toString());
			}
//			String msg = String
//					.format("{'title':'%s','description':'%s', 'custom_content':{'test':'test'}}",
//							title, message);
			request.setMessage(msg);

			// 5. 调用pushMessage接口
			PushUnicastMessageResponse response = channelClient
					.pushUnicastMessage(request);
			pushSuccessAmount = response.getSuccessAmount();

		} catch (ChannelClientException e) {
			// 处理客户端错误异常
			e.printStackTrace();
		} catch (ChannelServerException e) {
			// 处理服务端错误异常
			System.out.println(String.format(
					"request_id: %d, error_code: %d, error_message: %s",
					e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
		}
		return pushSuccessAmount;

	}

	//给所有用户发送通知
	public int pushAllMessage(String title, String message) {
		int pushSuccessAmount = 0;
		try {
			// 4. 创建请求类对象
			PushBroadcastMessageRequest request = new PushBroadcastMessageRequest();
			request.setDeviceType(3); // device_type => 1: web 2: pc 3:android
										// 4:ios 5:wp
			// request.setMessage("Hello Channel");
			// 若要通知，
			request.setMessageType(1);
			String msg = String.format("{'title':'%s','description':'%s'}",
					title, message);
			request.setMessage(msg);

			// 5. 调用pushMessage接口
			PushBroadcastMessageResponse response = channelClient
					.pushBroadcastMessage(request);

			// 6. 认证推送成功
			pushSuccessAmount = response.getSuccessAmount();

		} catch (ChannelClientException e) {
			// 处理客户端错误异常
			e.printStackTrace();
		} catch (ChannelServerException e) {
			// 处理服务端错误异常
			System.out.println(String.format(
					"request_id: %d, error_code: %d, error_message: %s",
					e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
		}
		return pushSuccessAmount;

	}

}