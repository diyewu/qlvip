package com.ql.controller.weixin;

public class WeixinConstants {
	public static final String WEIXIN_TOKEN = "weinxin_token";
	public static final String WEIXIN_WEB_AUTH_TOKEN = "weixin_web_auth_token";
	public static final String WEIXIN_OPEN_ID = "weixin_open_id";
	
	
	/**
	 * session 相关
	 */
	//微信openid
	public static final String SESSION_WEIXIN_OPEN_ID = "session_weixin_open_id";
	//获取验证码手机号
	public static final String SESSION_WEIXIN_USER_MOBILE = "session_weixin_user_mobile";
	//手机验证码
	public static final String SESSION_MOBILE_VALIDATE_CODE = "session_mobile_validate_code";
	//用户ID
	public static final String SESSION_MEMBER_ID = "session_member_id";
	
    // 各种消息类型,除了扫带二维码事件
    /**
     * 文本消息
     */
    public static final String MESSAGE_TEXT = "text";
    /**
     * 图片消息
     */
    public static final String MESSAtGE_IMAGE = "image";
    /**
     * 图文消息
     */
    public static final String MESSAGE_NEWS = "news";
    /**
     * 语音消息
     */
    public static final String MESSAGE_VOICE = "voice";
    /**
     * 视频消息
     */
    public static final String MESSAGE_VIDEO = "video";
    /**
     * 小视频消息
     */
    public static final String MESSAGE_SHORTVIDEO = "shortvideo";
    /**
     * 地理位置消息
     */
    public static final String MESSAGE_LOCATION = "location";
    /**
     * 链接消息
     */
    public static final String MESSAGE_LINK = "link";
    /**
     * 事件推送消息
     */
    public static final String MESSAGE_EVENT = "event";
    /**
     * 事件推送消息中,事件类型，subscribe(订阅)
     */
    public static final String MESSAGE_EVENT_SUBSCRIBE = "subscribe";
    /**
     * 事件推送消息中,事件类型，unsubscribe(取消订阅)
     */
    public static final String MESSAGE_EVENT_UNSUBSCRIBE = "unsubscribe";
    /**
     * 事件推送消息中,上报地理位置事件
     */
    public static final String MESSAGE_EVENT_LOCATION_UP = "LOCATION";
    /**
     * 事件推送消息中,自定义菜单事件,点击菜单拉取消息时的事件推送
     */
    public static final String MESSAGE_EVENT_CLICK = "CLICK";
    /**
     * 事件推送消息中,自定义菜单事件,点击菜单跳转链接时的事件推送
     */
    public static final String MESSAGE_EVENT_VIEW = "VIEW";
}
