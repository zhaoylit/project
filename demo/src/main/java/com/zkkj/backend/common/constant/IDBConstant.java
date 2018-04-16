package com.zkkj.backend.common.constant;

public interface IDBConstant {

	public static final int NULL_PARENT_KEY = -1;// 空父字典键
	
	public static final int PARAMETER_FIXED_KEY = 1;//取得参数的键（此处固定为1）
	
	//service_info业务代码类型
	public static final String SERVICE_RIGHT_HERE = "SERVICE_RIGHT_HERE";// 业务代码正在
	
	public static final String SERVICE_WANT_GO = "SERVICE_WANT_GO";// 业务代码想去
	
	public static final String SERVICE_BEEN_THERE = "SERVICE_BEEN_THERE";// 业务代码去过	
	
	public static final String SERVICE_HEAD_PHOTO = "SERVICE_HEAD_PHOTO";// 业务代码头像
	 
	public static final String SERVICE_TYPE = "SERVICE_TYPE";// 业务类型

	public static final String SERVICE_TYPE_RIGHT_HERE = "1";// 正在

	public static final String SERVICE_TYPE_WANT_GO = "2";// 想去

	public static final String SERVICE_TYPE_BEEN_THERE = "3";// 去过
	
	public static final String SERVICE_TYPE_TOGETHER = "4";//拼吧分享
	
	public static final String GENDER = "GENDER";// 性别

	public static final String GENDER_MALE = "1";// 男

	public static final String GENDER_FEMALE = "2";// 女

	public static final String STATUS = "STATUS"; // 状态

	public static final String STATUS_VALID = "1"; // 有效

	public static final String STATUS_INVALID = "2"; // 无效

	public static final String MESSAGE_TYPE = "MESSAGE_TYPE"; // 消息类型

	public static final String MESSAGE_TYPE_SYSTEM = "1"; // 系统消息

	public static final String MESSAGE_TYPE_USER = "2"; // 用户消息

	public static final String MESSAGE_OWNER_TYPE = "MESSAGE_OWNER_TYPE"; // 消息属主类型

	public static final String MESSAGE_OWNER_TYPE_ADD_FRIEND = "1"; // 添加好友验证信息

	public static final String MESSAGE_OWNER_TYPE_WAVE = "2";// 正在摇一摇发送信息

	public static final String MESSAGE_OWNER_TYPE_TO_FRIEND = "3"; // @好友

	public static final String MESSAGE_OWNER_TYPE_PRAISE = "4"; // 点赞

	public static final String MESSAGE_OWNER_TYPE_COMMENT = "5"; // 评论

	public static final String MESSAGE_OWNER_TYPE_REPLY = "6"; // 回复
	
	public static final String MESSAGE_OWNER_TYPE_TO_REMIND = "7"; // 发布分享提醒谁来看

	public static final String MESSAGE_OWNER_TYPE_NEARBY_FRIENDS = "8"; // 附近好友
	
	public static final String MESSAGE_OWNER_TYPE_PUBLISH_WANT_GO = "9";//发布想去
	
	public static final String MESSAGE_OWNER_TYPE_PARTICIPATE_WANT_GO = "10";//参与想去
	
	public static final String MESSAGE_OWNER_TYPE_FRIENDS_RECOMMEND = "11";//好友推荐
	
	public static final String MESSAGE_STATUS = "MESSAGE_STATUS"; // 读状态

	public static final String MESSAGE_STATUS_UNREAD = "1"; // 未读

	public static final String MESSAGE_STATUS_READ = "2"; // 已读
	
	public static final String MESSAGE_STATUS_DELETE = "3"; // 已删

	public static final String RELATION_TYPE = "RELATION_TYPE"; // 关系类型

	public static final String RELATION_TYPE_FRIEND = "1";// 好友

	public static final String RELATION_TYPE_BLACK = "2"; // 好友拉黑
	
	public static final String RELATION_TYPE_NOT_VALID = "3"; // 未验证
	
	public static final String RELATION_TYPE_SENDED = "4"; // 已发送
	
	public static final String RELATION_TYPE_BLACK_STRANGER = "5"; // 陌生人拉黑

	public static final String OPERATOR_TYPE = "OPERATOR_TYPE";// 登录账号类型

	public static final String OPERATOR_TYPE_VERIFIED = "1";// 已验证

	public static final String OPERATOR_TYPE_NOT_VERIFIED = "2";// 未验证
	
	public static final String RESOURCE_TYPE = "RESOURCE_TYPE";//资源类型
	
	public static final String RESOURCE_TYPE_PIC = "1";//图片
	
	public static final String RESOURCE_TYPE_VIDEO = "2";//视频
	
	public static final String RESOURCE_TYPE_AUDIO = "3";//声音
	
	public static final String RESOURCE_TYPE_FILE = "4";//文件
	
	public static final String RESOURCE_TYPE_PLAIN_TEXT = "5";//普通文本
	
	public static final String RESOURCE_TYPE_HTML = "6";//普通文本
	
	public static final String RESOURCE_TYPE_HEAD_PIC = "101";//头像
	
	public static final String RESOURCE_TYPE_QRCODE = "102";//二维码
	
	public static final String DOMAIN_NAME = "DOMAIN_NAME";//域名
	
	public static final String DOMAIN_NAME_STRING = "1";//域名字符串
	
	public static final String DOMAIN_NAME_BINDING_IP = "2";//域名绑定ip
	
	public static final String DRIVE = "DRIVE";//盘符
	
	public static final String DRIVE_STRING_F = "1";//F盘符
	
	public static final String DRIVE_STRING_D = "2";//D盘符
	
	public static final String CODE_TYPE_REGISTER = "1";//注册验证码
	
	public static final String CODE_TYPE_UPDATE_MOBILE = "2";//修改手机号验证码
	
	public static final String CODE_TYPE_FIND_PASSWORD = "3";//找回密码验证码
	
	public static final int MESSAGE_RECEIVE_ID_ALL = -1;//消息接收者（所有人）
	
	public static final int MESSAGE_RECEIVE_ID_SYSTEM = -2;//消息接收者（系统）
	
	public static final String RANK = "RANK";//等级
	
	public static final String RANK_ONE = "1";//等级1
	
	public static final String RANK_TWO = "2";//等级2
	
	public static final String RANK_THREE = "3";//等级3
	
	public static final String LOGIC_STATUS = "LOGIC_STATUS";//逻辑状态
	
	public static final String LOGIC_STATUS_YES = "1";//逻辑状态是
	
	public static final String LOGIC_STATUS_NO = "2";//逻辑状态否
	
	public static final String SHOW_STATUS = "SHOW_STATUS";//显示状态
	
	public static final String SHOW_STATUS_YES = "1";//显示状态是
	
	public static final String SHOW_STATUS_NO = "2";//显示状态否
	
	public static final String RESORT_TYPE ="RESORT_TYPE";//场地类型
	
	public static final String AREA_TAG = "AREA_TAG";//区域标签
	
	public static final String RESORT_TAG = "RESORT_TAG";//场地标签
	
	public static final String HEART_STATUS = "HEART_STATUS";//存在状态
	
	public static final String HEART_STATUS_YES = "1";//有评论无桃心
	
	public static final String HEART_STATUS_NO = "2";//有评论无桃心
	
	public static final String EXIST_STATUS = "EXIST_STATUS";//存在状态
	
	public static final String EXIST_STATUS_YES = "1";//有评论无桃心
	
	public static final String EXIST_STATUS_NO = "2";//无评论有桃心
	
	public static final String EXIST_STATUS_ALL_YES = "3";//有桃心有评论
	
	public static final String EXIST_STATUS_ALL_NO = "4";//无桃心无评论
	
	public static final String PUBLISH_TYPE = "PUBLISH_TYPE";//发布类型
	
	public static final String PUBLISH_TYPE_AREA = "1";//区域类型
	
	public static final String PUBLISH_TYPE_RESORT = "2";//场地类型
	
	public static final String PUBLISH_TYPE_TOUR = "3";//约游类型
	
	public static final String PUBLISH_STATUS = "PUBLISH_STATUS";//发布状态
	
	public static final String PUBLISH_STATUS_BEING = "1";//进行中
	
	public static final String PUBLISH_STATUS_CLOSED = "2";//已关闭
	
	public static final String PUBLISH_STATUS_EXPIRED = "3";//已过期
	
	public static final String PUBLISH_STATUS_DELETED = "4";//已删除
	
	public static final String PARTICIPANT_STATUS = "PARTICIPANT_STATUS";//加入状态
	
	public static final String PARTICIPANT_STATUS_APPLICATION = "1";//申请中
	
	public static final String PARTICIPANT_STATUS_PASSED = "2";//已通过
	
	public static final String PARTICIPANT_STATUS_REJECTED = "3";//已被拒
	
	public static final String PARTICIPANT_STATUS_DELETED = "4";//已删除
	
	///////////////////////////////////////聊天开始/////////////////////////////////////
	public static final String GROUP_TYPE = "GROUP_TYPE";//群组类型
	
	public static final String GROUP_TYPE_GROUP = "1";//群
	
	public static final String GROUP_TYPE_DISCUSSION = "2";//讨论组
	
	public static final String MEMBER_TYPE = "MEMBER_TYPE";//成员类型
	
	public static final String MEMBER_TYPE_OWNER = "1";//群主
	
	public static final String MEMBER_TYPE_MANAGER = "2";//群管理员
	
	public static final String MEMBER_TYPE_COMMON = "3";//群成员
	
	public static final String CHAT_MESSAGE_TYPE = "CHAT_MESSAGE_TYPE";//聊天消息类型
	
	public static final String CHAT_MESSAGE_TYPE_PLAIN = "1";//文本
	
	public static final String CHAT_MESSAGE_TYPE_FILE = "2";//文件
	
	public static final String RECEIVE_STATUS = "RECEIVE_STATUS";//接收状态
	
	public static final String RECEIVE_STATUS_NOT = "1";//未接收（未完全接收）
	
	public static final String RECEIVE_STATUS_YES = "2";//已接收（全接收）
	
	public static final String CHAT_TYPE = "CHAT_TYPE";//聊天类型
	
	public static final String CHAT_TYPE_SINGLE = "1";//单聊
	
	public static final String CHAT_TYPE_GROUP = "2";//群聊
	
	
	///////////////////////////////////////聊天结束/////////////////////////////////////
	
	public static final String TRAVEL_TOGETHER_STATUS = "TRAVEL_TOGETHER_STATUS";//同行状态
	
	public static final String TRAVEL_TOGETHER_STATUS_VALID = "1";//有效
	
	public static final String TRAVEL_TOGETHER_STATUS_EXPIRED = "2";//过期
	
	public static final String TRAVEL_TOGETHER_STATUS_DELETED = "3";//删除
	
	public static final String USER_RELATION_TYPE = "USER_RELATION_TYPE";//用户关联关系
	
	public static final String USER_RELATION_TYPE_BOOK = "1";//通讯录
	
	public static final String USER_RELATION_TYPE_QQ = "2";//QQ
	
	public static final String USER_RELATION_TYPE_WEIBO = "3";//微博
	
	public static final String SMS_SEND_TYPE = "SMS_SEND_TYPE";//短信发送类型
	
	public static final String SMS_SEND_TYPE_REGISTER = "1";//注册验证码
	
	public static final String SMS_SEND_TYPE_FORGET_PASSWORD = "2";//忘记密码
	
	public static final String SMS_SEND_TYPE_UPDATE_USERMSISDN= "3";//修改手机号
	
	public static final String SMS_SEND_STATUS = "SMS_SEND_STATUS";//短信发送状态
	
	public static final String SMS_SEND_STATUS_SUCCESS = "0";//短信发送状态成功
	
	public static final String TERMINAL_TYPE = "TERMINAL_TYPE";//终端类型
	
	public static final String TERMINAL_TYPE_IOS = "1";//终端类型IOS
	
	public static final String TERMINAL_TYPE_ANDROID = "2";//终端类型Android
	
	public static final String TRIGGER_TYPE = "TRIGGER_TYPE";//触发类型
	
	public static final String TRIGGER_TYPE_MANUAL = "1";//人工触发
	
	public static final String TRIGGER_TYPE_AUTOMATION = "2";//自动触发
	
	public static final String UPLOAD_RESOURCE_TYPE = "UPLOAD_RESOURCE_TYPE";//上传资源类型
	
	public static final String UPLOAD_RESOURCE_TYPE_TEXT = "1";//上传资源类型文本
	
	public static final String UPLOAD_RESOURCE_TYPE_PIC = "2";//上传资源类型图片
	
	public static final String UPLOAD_RESOURCE_TYPE_VIDEO = "3";//上传资源类型视频
	
	public static final String UPLOAD_RESOURCE_TYPE_HEAD = "4";//上传资源类型头像
	
	public static final String UPLOAD_RESOURCE_STATUS = "UPLOAD_RESOURCE_STATUS";//上传资源状态
	
	public static final String UPLOAD_RESOURCE_STATUS_UPLOADED = "1";//上传资源状态已上传
	
	public static final String UPLOAD_RESOURCE_STATUS_USED = "2";//上传资源状态已使用
	
	public static final String SERVICE_ITEM = "SERVICE_ITEM";//服务项目
	
	public static final String SHIELD_STATUS = "SHIELD_STATUS";//屏蔽状态
	
	public static final String SHIELD_STATUS_NOT = "1";//屏蔽状态否
	
	public static final String SHIELD_STATUS_YES = "2";//屏蔽状态是
	
	public static final String ACTIVITY_TYPE = "ACTIVITY_TYPE";//活动类型
	
	public static final String APP_TYPE = "APP_TYPE";//APP类型
	
	public static final String RECRUIT_STATUS = "RECRUIT_STATUS";//招募状态
	
	public static final String RECRUIT_STATUS_REGISTERED = "1";//已报名
	
	public static final String RECRUIT_STATUS_RECRUITED = "2";//已招募
	
	public static final String SYSTEM_OPERATOR = "0";  //系统
	public static final String GROUP_SERVICE_TYPE = "GROUP_SERVICE_TYPE";//群组业务类型
	
	public static final String GROUP_SERVICE_TYPE_WANT_GO  = "1";//群组业务类型想去
	
	public static final String REPORT_TYPE = "REPORT_TYPE"; //举报类型
	
	public static final String REPORT_TYPE_YELLOW = "1"; //举报类型：色情低俗
	
	public static final String REPORT_TYPE_GRAY = "2"; //举报类型：广告骚扰
	
	public static final String REPORT_TYPE_PUBLICIST = "3"; //举报类型：政治敏感
	
	public static final String REPORT_TYPE_CANARD = "4"; //举报类型：谣言
	
	public static final String REPORT_TYPE_DECEIVER = "5"; //举报类型：欺诈骗钱
	
	public static final String REPORT_TYPE_TRANSGRESS = "6"; //举报类型：违法（暴力恐怖、违禁品等）
	
	public static final String REPORT_TYPE_INTRUSION = "7"; //举报类型：侵权举报（诽谤、抄袭、冒用）
	
	public static final String REPORT_STATUS = "REPORT_STATUS"; //举报处理
	
	public static final String REPORT_STATUS_NOT = "1"; //举报处理：未处理
	
	public static final String REPORT_STATUS_YES = "2"; //举报处理: 已处理
	
	public static final String PLAYMATE_STATUS = "PLAYMATE_STATUS";//玩伴状态
	
	public static final String PLAYMATE_STATUS_CHECK = "1";//玩伴状态审核

	public static final String PLAYMATE_STATUS_PASS = "2";//玩伴状态通过
	
	public static final String PLAYMATE_STATUS_REFUSED = "3";//玩伴状态被拒
	
	public static final String PAY_SERVICE_TYPE = "PAY_SERVICE_TYPE";//支付业务类型
	
	public static final String PAY_SERVICE_TYPE_TO_PLAY = "1";//支付业务类型支付玩伴
	
	public static final String PAY_SERVICE_TYPE_TO_INN = "2";//支付业务类型支付玩伴
	
	public static final String PAY_SERVICE_TYPE_TO_AUTO = "3";//支付业务类型支付玩伴	
	
	public static final String APPRAISE_TYPE = "APPRAISE_TYPE";//评价类型
	
	public static final String APPRAISE_TYPE_PLAYMATE = "1";//评价类型评价玩伴
	
	public static final String APPRAISE_TYPE_INN = "2";//评价类型评价客栈
	
	public static final String APPRAISE_TYPE_AUTO = "3";//评价类型评价用车
	
	public static final String DEAL_STATUS = "DEAL_STATUS";//处理状态
	
	public static final String DEAL_STATUS_NO = "1";//处理状态未处理
	
	public static final String DEAL_STATUS_YES = "2";//处理状态已处理
	
	public static final String DEAL_STATUS_REPLAY = "3";//处理状态已回复
	
	public static final String DEAL_STATUS_NO_REFUND = "4";//处理状态不予退款
	
	public static final String PAY_TYPE = "PAY_TYPE";//支付类型
	
	public static final String PAY_TYPE_ALIPAY = "1";//支付宝支付
	
	public static final String PAY_TYPE_WXPAY = "2";//微信支付
	
	public static final String PAY_STATUS = "PAY_STATUS";//支付状态
	
	public static final String PAY_STATUS_NOT = "1";//未支付
	
	public static final String PAY_STATUS_OK_FIRST = "20";//已支付首款
	
	public static final String PAY_STATUS_OK = "2";//已支付全部
	
	public static final String PAY_STATUS_REFUND = "3";//已退款

	public static final String PLAY_STATUS = "PLAY_STATUS";//陪玩状态
	
	public static final String PLAY_STATUS_APPLY = "10";//陪玩状态待确认
	
	public static final String PLAY_STATUS_CONFIRMED = "20";//陪玩状态已确认
	
	public static final String PLAY_STATUS_CANCELLED = "201";//陪玩状态已取消
	
	public static final String PLAY_STATUS_STARTED = "30";//陪玩状态已开始
	
	public static final String PLAY_STATUS_INTERRUPTED = "301";//陪玩状态已中止
	
	public static final String PLAY_STATUS_ENDED = "40";//陪玩状态已结束
	
	public static final String PLAY_STATUS_APPRAISED = "50";//陪玩状态已评价
	
	public static final String TIME_TYPE = "TIME_TYPE";//专兼职类型
	
	public static final String TIME_TYPE_FULL = "1";//专职
	
	public static final String TIME_TYPE_HALF = "2";//兼职
	
	public static final String EXCHANGE_FREQUENCY = "EXCHANGE_FREQUENCY";//兑换频度
	
	public static final String EXCHANGE_FREQUENCY_TIMES = "1";//兑换频度次
	
	public static final String EXCHANGE_FREQUENCY_FULL_DAY = "2";//兑换频度全天
	
	public static final String EXCHANGE_FREQUENCY_HALF_DAY = "3";//兑换频度半天
	
	public static final String EXCHANGE_TYPE = "EXCHANGE_TYPE";//兑换类型
	
	public static final String EXCHANGE_TYPE_REGISTERE = "1";//兑换类型注册
	
	public static final String EXCHANGE_TYPE_WANT_GO = "2";//兑换类型发布想去
	
	public static final String EXCHANGE_TYPE_RIGHT_HERE = "3";//兑换类型发布正在
	
	public static final String EXCHANGE_TYPE_SHARE = "4";//兑换类型转发分享
	
	public static final String EXCHANGE_TYPE_DOWNLOAD = "5";//兑换类型邀请好友下载关注
	
	public static final String EXCHANGE_TYPE_PLAY = "6";//兑换类型约游成功
	
	public static final String ACCOUNT_SERVICE_TYPE = "ACCOUNT_SERVICE_TYPE";//兑换类型
	
	public static final String ACCOUNT_SERVICE_TYPE_REGISTERE = "1";//兑换类型注册
	
	public static final String ACCOUNT_SERVICE_TYPE_WANT_GO = "2";//兑换类型发布想去
	
	public static final String ACCOUNT_SERVICE_TYPE_RIGHT_HERE = "3";//兑换类型发布正在
	
	public static final String ACCOUNT_SERVICE_TYPE_SHARE = "4";//兑换类型转发分享
	
	public static final String ACCOUNT_SERVICE_TYPE_DOWNLOAD = "5";//兑换类型邀请好友下载关注
	
	public static final String ACCOUNT_SERVICE_TYPE_PLAY = "6";//兑换类型约游成功
	
	public static final String ACCOUNT_SERVICE_TYPE_PLAY_PAY = "101";//兑换类型玩伴支付
	
	public static final String ACCOUNT_SERVICE_TYPE_INN = "102";//兑换类型玩伴支付
	
	public static final String ACCOUNT_SERVICE_TYPE_AUTO = "103";//兑换类型玩伴支付
	
	public static final String COMPLAIN_TYPE = "COMPLAIN_TYPE";//投诉类型
	
	public static final String COMPLAIN_TYPE_PLAYMATE = "1";//投诉玩伴
	
	public static final String COMPLAIN_TYPE_INN= "2";//投诉客栈
	
	public static final String COMPLAIN_TYPE_RENT_AUTO = "3";//投诉用车
	
	public static final String PREPAY_TYPE = "PREPAY_TYPE";//预付类型
	
	public static final String PREPAY_TYPE_AMOUNT = "1";//按金额预付
	
	public static final String PREPAY_TYPE_PERCENT = "2";//按比例预付
	
	public static final String TOGETHER_TYPE = "TOGETHER_TYPE";//拼吧类型
	
	public static final String TOGETHER_TYPE_CARPOOL = "1";//拼吧类型拼车
	
	public static final String TOGETHER_TYPE_TOUR = "2";//拼吧类型约游
	
	public static final String ROOM_TYPE = "ROOM_TYPE";//房间类型
	
	public static final String PRICE_TYPE = "PRICE_TYPE";//价格类型
	
	public static final String PRICE_TYPE_COMMON = "1";//价格类型通用
	
	public static final String PRICE_TYPE_WEEK = "2";//价格类型周
	
	public static final String PRICE_TYPE_HOLIDAY = "3";//价格类型特殊日期
	
	public static final String INN_ORDER_STATUS = "INN_ORDER_STATUS";//客栈订单状态
	
	public static final String INN_ORDER_STATUS_VALID = "10";//客栈订单状态有效
	
	public static final String INN_ORDER_STATUS_NOT_CONFIRMED = "101";//客栈订单状态未确认
	
	public static final String INN_ORDER_STATUS_CONFIRMED = "102";//客栈订单状态已确认
	
	public static final String INN_ORDER_STATUS_NOT_CANCELLED = "20";//客栈订单状态被取消
	
	public static final String INN_ORDER_STATUS_DELETED = "30";//客栈订单状态被删除
	
	public static final String RESOURCE_SERVICE_TYPE = "RESOURCE_SERVICE_TYPE";//资源业务类型
	
	public static final String RESOURCE_SERVICE_TYPE_PLAYMATE = "1";//资源业务类型玩伴
	
	public static final String RESOURCE_SERVICE_TYPE_INN_PHOTO = "2";//资源业务类型客栈照片
	
	public static final String RESOURCE_SERVICE_TYPE_INN = "201";//资源业务类型客栈
	
	public static final String RESOURCE_SERVICE_TYPE_INN_BUSINESS_LICENCE = "202";//资源业务类型客栈营业执照
	
	public static final String RESOURCE_SERVICE_TYPE_INN_ID_PHOTO = "203";//资源业务类型客栈身份证照片
	
	public static final String RESOURCE_SERVICE_TYPE_DRVIER_PHOTO = "3";//资源业务类型用车照片
	
	public static final String RESOURCE_SERVICE_TYPE_DRVIER_CARD= "301";//资源业务类型驾驶证
	
	public static final String RESOURCE_SERVICE_TYPE_DRVIER_LICENSE = "302";//资源业务类型行驶证
	
	public static final String RESOURCE_SERVICE_TYPE_DRVIER_COMPULSORY_INSURANCE= "303";//资源业务类型交强险
	
	public static final String RESOURCE_SERVICE_TYPE_DRVIER_COMMERCIAL_INSURANCE = "304";//资源业务类型商业险
	
	public static final String RESOURCE_SERVICE_TYPE_DRVIER_ONESELF = "305";//资源业务类型司机本人
	
	public static final String APPRAISE_STATUS = "APPRAISE_STATUS";//评价状态
	
	public static final String APPRAISE_STATUS_VALID = "1";//评价状态有效
	
	public static final String APPRAISE_STATUS_BLOCK = "2";//评价状态屏蔽
	
	public static final String APPRAISE_STATUS_INVALID = "3";//评价状态无效
	
	public static final String OCCUPANCY_STATUS = "OCCUPANCY_STATUS";//入住状态
	
	public static final String OCCUPANCY_STATUS_OPEN = "1";//入住状态打开
	
	public static final String OCCUPANCY_STATUS_CLOSE = "2";//入住状态关闭
	
	public static final String ROOM_SALE_TYPE = "ROOM_SALE_TYPE";//房间销售类型
	
	public static final String ROOM_SALE_TYPE_ROOM = "1";//房间销售类型房间
	
	public static final String ROOM_SALE_TYPE_BED = "2";//房间销售类型床位
	
	public static final String EQUIP_TYPE = "EQUIP_TYPE";//设施类型
	
	public static final String EQUIP_TYPE_TXT = "1";//设施类型选择
	
	public static final String EQUIP_TYPE_CHECK = "2";//设施类型选择
	

	public static final String AUTO_TYPE = "AUTO_TYPE";//车型
	
	public static final String SEAT_NUM = "SEAT_NUM";//座位数
	
	public static final String SERVICE_STATUS = "SERVICE_STATUS";//接单状态
	
	public static final String SERVICE_STATUS_YES = "1";//接单状态接单

	public static final String SERVICE_STATUS_NO = "2";//接单装填不接单
	
	public static final String BLOCK_TYPE = "BLOCK_TYPE";//片区
	
	public static final String BLOCK_TYPE_BLOCK = "1";//片区片区

	public static final String BLOCK_TYPE_AIRPORT= "2";//片区机场
	
	public static final String BLOCK_TYPE_STATION = "3";//片区车站
	
	public static final String BLOCK_TYPE_INN = "4";//片区客栈
	
	public static final String COUPONS_TYPE = "COUPONS_TYPE";//优惠券类型
	
	public static final String COUPONS_TYPE_DISCOUNT_SHUTTLE = "1";//优惠券类型一元送机
	
	public static final String USE_STATE = "USE_STATE";//使用状态
	
	public static final String USE_STATE_NOT = "1";//使用状态未用
	
	public static final String USE_STATE_YES = "2";//使用状态已用
	
	public static final String COUPONS_DEAL_STATUS = "COUPONS_DEAL_STATUS";//优惠券发放状态
	
	public static final String COUPONS_DEAL_STATUS_UNTREATED = "1";//优惠券发放状态未处理
	
	public static final String COUPONS_DEAL_STATUS_GRANT = "2";//优惠券发放状态已发放

	public static final String COUPONS_DEAL_STATUS_REFUSED = "3";//优惠券发放状态不发放
	
	public static final String ROAD_TYPE = "ROAD_TYPE";//线路类型
	
	public static final String ROAD_TYPE_YES = "1";//线路类型线路
	
	public static final String ROAD_TYPE_NOT = "2";//线路类型非线路

	public static final String EXPENSE_STATUS = "EXPENSE_STATUS";//费用担负
	
	public static final String EXPENSE_STATUS_NOT_INVOLVED = "1";//不涉及
	
	public static final String EXPENSE_STATUS_CUSTOMER_PAY = "2";//客户承担
	
	public static final String EXPENSE_STATUS_CUSTOMER_NOT_PAY = "3";//客户不负责
	
	public static final String SHUTTLE_TYPE = "SHUTTLE_TYPE";//费用担负
	
	public static final String SHUTTLE_TYPE_PICKUP = "1";//接
	
	public static final String SHUTTLE_TYPE_PEOPLE = "2";//送
	
	public static final String AREA_TYPE = "AREA_TYPE";//区域类型(城市类型)
	
	public static final String AREA_TYPE_TOURIST_CITY = "1";//旅游城市
	
	public static final String AREA_TYPE_NOT_TOURIST_CITY  = "2";//非旅游城市

	public static final String RENT_AUTO_TYPE = "RENT_AUTO_TYPE";//用车类型
	
	public static final String RENT_AUTO_TYPE_RENT_AUTO = "1";//包车
	
	public static final String RENT_AUTO_TYPE_RENT_AUTO_ROAD  = "101";//线路包车
	
	public static final String RENT_AUTO_TYPE_RENT_AUTO_NOT_ROAD  = "102";//非线路包车

	public static final String RENT_AUTO_TYPE_SHUTTLE= "2";//接送机
	
	public static final String RENT_AUTO_TYPE_SHUTTLE_TOURIST_CITY  = "201";//旅游城市接送机
	
	public static final String RENT_AUTO_TYPE_SHUTTLE_NOT_TOURIST_CITY  = "202";//非旅游城市接送机
	
	public static final String RENT_AUTO_TYPE_PEOPLE_FREE_CHARGE= "3";//免费送机

	public static final String PAY_MODE = "PAY_MODE";//付款方式
	
	public static final String PAY_MODE_ONCE= "1";//一次性付款
	
	public static final String PAY_MODE_INSTALMENT_TWICE = "2";//两次分期付款

	public static final String RENT_ORDER_STATUS = "RENT_ORDER_STATUS";//用车订单状态
	
	public static final String RENT_ORDER_STATUS_ORDER= "1";//用车订单状态游客已下单
	
	public static final String RENT_ORDER_STATUS_CANCELED_BY_CUSTOMER = "2";//用车订单状态游客已取消
	
	public static final String RENT_ORDER_STATUS_NOTIFIED = "3";//用车订单状态司机被通知
	
	public static final String RENT_ORDER_STATUS_CONFIRMED  = "4";//用车订单状态司机已接单
	
	public static final String RENT_ORDER_STATUS_CANCELED_BY_DRIVER = "5";//用车订单状态司机已取消
	
	public static final String RENT_ORDER_STATUS_STARTED = "6";//用车订单状态司机已开始
	
	public static final String RENT_ORDER_STATUS_OVER= "7";//用车订单状态司机已结束
	
	public static final String RENT_ORDER_STATUS_DELETE= "8";//用车订单状态删除
	
	public static final String SETTLEMENT_STATUS = "SETTLEMENT_STATUS";//结算状态
	
	public static final String SETTLEMENT_STATUS_NOT = "1";//结算状态未结算
	
	public static final String SETTLEMENT_STATUS_YES = "2";//结算状态已结算

	public static final String BALANCE_PAYMENT_TYPE= "BALANCE_PAYMENT_TYPE";//收支类型
	
	public static final String BALANCE_PAYMENT_TYPE_IN = "1"; //收入
	
	public static final String BALANCE_PAYMENT_TYPE_OUT = "2"; //支出
	
	public static final String COUPONS_ORIGIN_TYPE = "COUPONS_ORIGIN_TYPE"; //优惠券来源类型
	
	public static final String COUPONS_ORIGIN_TYPE_INN_ORDER = "1"; //客栈下单
	
	public static final String COUPONS_ORIGIN_TYPE_COUPONS_REQUEST = "2"; //优惠券申请
	
	public static final String AUDIT_STATUS= "AUDIT_STATUS";//审核状态
	
	public static final String AUDIT_STATUS_YES = "1"; //审核通过
	
	public static final String AUDIT_STATUS_NO = "2"; //审核不通过
	
	public static final String AUDIT_STATUS_BEING = "3"; //审核中
	
	/**用车订单支付类型**/
	public static final String AUTO_ORDER_PAY_TYPE_0 = "0";//全额支付
	public static final String AUTO_ORDER_PAY_TYPE_1 = "1";//首次支付
	public static final String AUTO_ORDER_PAY_TYPE_2 = "2";//第二次支付(支付余款)
	
	public static final String APPRAISE_TYPE_LINE = "4"; //线路评价
	public static final String APPRAISE_TYPE_SPIRIT = "5"; //精灵评价
	
	public static final String RESOURCE_IMAGE_SERVICETYPE_LINE = "701"; //线路图片
	public static final String RESOURCE_IMAGE_SERVICETYPE_SPIRIT = "801"; //精灵图片
	public static final String RESOURCE_IMAGE_SERVICETYPE_CAROUSEL = "900"; //首页轮播图
	
	public static final String SERVICE_TYPE_INN = "100"; //客栈
	public static final String SERVICE_TYPE_LINE = "300"; //线路
	public static final String SERVICE_TYPE_SPIRIT = "400"; //精灵生活
	
	public static final String LINE_ORDER_STATUS_VALID = "10";//线路订单状态有效
	public static final String LINE_ORDER_STATUS_INVALID = "20";//线路订单状态有效
	public static final String LINE_ORDER_STATUS_DELETE = "30";//线路订单已删除状态
	
	public static final String RECORD_STATUS_VALID = "1";
	public static final String RECORD_STATUS_INVALID = "2";
	
	public static final String ORDER_SERVICE_TYPE_INN = "1"; //客栈订单
	public static final String ORDER_SERVICE_TYPE_AUTO = "2"; //用车订单
	public static final String ORDER_SERVICE_TYPE_LINE = "3"; //线路订单
	public static final String ORDER_SERVICE_TYPE_SPIRIT = "4"; //精灵生活订单
}
