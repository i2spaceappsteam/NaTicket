package com.NaTicket.n.utils;

/**
 * Created by Ankit on 8/16/2017.
 */

public class Constants {

    //Test
   /* public  static  final  String BASEURL           = "http://webapi.i2space.co.in/";
    public  static  final  String ConsumerKey       = "AEBB856E87A0B01562197ABA066BF28C8E9E6C6C";
    public  static  final  String ConsumerSecret    = "04DAF1E2C80A758DCC6BCEBE436D25BC";*/


    //Live
    public  static  final  String BASEURL          = "http://35.154.194.198:86/";
    public  static  final  String ConsumerKey       = "F24D04138C7C2CCB345468B5A5599FCCC699F624";
    public  static  final  String ConsumerSecret    = "B7CD40E69B7602937B272105329DD2D8";


    //////PayUmoney TEST
   /* public  static  final  String PayUmoneyMerchantKey    = "LLKwG0";
    public  static  final  String PayUmoneyMerchantID    = "393463";
    public  static  final  String PayUmoneyMerchantSALT    = "qauKbEAJ";
    public  static  final  Boolean PayUmoneyMerchantIsDebug    = true;*/


    //////PayUmoney LIVE
    public  static  final  String PayUmoneyMerchantKey       = "ZDBqZmfu";
    public  static  final  String PayUmoneyMerchantID        = "6109335";
    public  static  final  String PayUmoneyMerchantSALT      = "4zYBtleBZJ";
    public  static  final  Boolean PayUmoneyMerchantIsDebug    = false;

    public  static  final  String FLIGHTS_IMAGE_URL = "http://webapi.etravos.com/Images/airline_logos/";

    public  static  final String emailPattern       = "[a-zA-Z0-9._-]+@[a-z0-9]+\\.+[a-z]+";

    public static String VERSIONCODE="http://webapi.i2space.co.in/Admin/GetAppsVersionControl?ClientId=";

    public static final String logoURL              ="http://obibo.in/serverdata/images/";//"http://psaobibo.i2space.co.in/serverdata/images/";//
    public static String CLIENTDETAILS              =BASEURL+"Accounts/clientDetails";

    public static final String Sources              ="Buses/sources";
    public static final String AvailableBuses       ="Buses/AvailableBuses?";
    public static final String TripDetails          ="Buses/TripDetails";
    public static final String BlockBusTicket       ="Buses/BlockBusTicket";
    //public static final String SavePaymentDetails   ="Accounts/SavePaymentDetails";

  public static String GenerateHash               =BASEURL+"admin/GeneratehashKey?";
  public static String ReGenerateHash               =BASEURL+"admin/ReGeneratehashKey?";

    public static String SAVEPAYMENTDETAILS         =BASEURL+"Accounts/SavePaymentDetails";
    public static String UPDATEPAYMENTDETAILS       =BASEURL+"Accounts/UpdatePaymentDetails";
    public static final String BookBusTicket        ="Buses/BookBusTicket?referenceNo=";

    public static final String BookingDetails       =BASEURL+"Buses/BusTicketBookingDetails?";
    public static final String CancelBusTicket      ="Buses/CancelBusTicket?";
    public static String REPORTS                    =BASEURL+"Admin/GetReports";

    public static String FEEDBACK                   =BASEURL+"Accounts/FeedbackComplaint";

    public static String USERLOGIN                  =BASEURL+"Accounts/UserLogin";
    public static String USERDETAILS                =BASEURL+"Accounts/UserDetails?username=";
    public static String AGENTLOGIN                 =BASEURL+"Accounts/AgentLogin";
    public static String AGENTDETAILS               =BASEURL+"Accounts/AgentDetails?username=";
    public static final String USERREGISTRATION     =BASEURL+"Accounts/UserRegistration";
    public static final String AGENTREGISTRATION    =BASEURL+"Accounts/AgentRequest";

    public static String AGENTLIST                  =BASEURL+"Accounts/AgentsList?userType=2";
    public static String FORGOTPASSWORD             =BASEURL+"Accounts/ForgotPassword";
    public static final String VERIFYUSER           ="Accounts/Userverfiy?username=";

    public static final String OTP                  ="Accounts/GetOTP?mobileNumber=";
    public static final String VerifyOtp            ="Accounts/VerifyOTP?key=";
    public static String GETPROMOCODES              =BASEURL+"Accounts/PromoCodes?AppType=2";
    public static final String ValidatePromo        ="Accounts/PromoUservalidation?Promocode=";

    public static String DECRIPTCODE                = BASEURL+"Admin/GetDecryptCode?Input=";
    public static final String EncriptCode          ="Admin/GetEncrptCode";
    public static final String Offers               = "Accounts/NotificationsAndOffers";
    public static final String Banners              = "Accounts/loadbigbanners";
    public static String DYNAMICPAGECONTENT         =BASEURL+"Accounts/DynamicPageContent";
    public static String CHANGEPASSWORD             = BASEURL+"Accounts/ChangePassword";
    public static final String CURRENCY             = BASEURL+"Accounts/Getcurrency?";

//////********Recharge Api Urls **********///////

    public static final String operatorsurl=BASEURL+"Recharges/RechargeOperators?rechargeType=";
    public static final String initiatercurl="Recharges/InitiateRecharge";
    public static final String rechargeurl =BASEURL+"Recharges/Recharge?referenceNo=";
    public static final String rechargestatusurl =BASEURL+"Recharges/RechargeStatus?referenceNo=";
    public static final String getreportsurl="Admin/GetReports";
    public static final String getConvenienceFee=BASEURL+"Recharges/RechargeConvenienceFee?userType=";
    public static final String promocode="accounts/PromoCodes?AppType=Mobile";

    public static final String getUtilConvenienceFee=BASEURL+"Bills/BillsConvenienceFee?userType=";


    ///!******------Flights Urls Services-------********//////

    public static  String GETAIRPORTS=BASEURL+"Flights/Airports?";
    public static  String AVAILABLEFLIGHTS=BASEURL+"Flights/AvailableFlights?";
    public static  String GetFareRule=BASEURL+"Flights/GetFareRule?";
    public static  String GetTaxDetails=BASEURL+"Flights/GetTaxDetails";
    public static  String BlockFlightTicket=BASEURL+"Flights/BlockFlightTicket";
    public static  String FlightBookingTicket=BASEURL+"Flights/BookFlightTicket?referenceNo=";
    public static  String FlightTicketDetails=BASEURL+"Flights/FlightTicketBookingDetails?referenceNo=";
    public static  String CancelFlightTicket=BASEURL+"Flights/CancelFlightTicket?";



    ///!******------Hotels Urls Services-------********//////

    public static final String NO_INT_MSG = "No internet Connection";
    public static final String SUCCESS_RESPONSE = "Success";
    public static final String DUPLICATE_RESPONSE = "Duplicate";
    public static String MYPREFERENCES ="AppPrefs";

    public static final String GETHOTELCITIES        ="http://webapi.i2space.co.in/Hotels/GetMatchingCities?" ;
    public static String GETHOTELS=BASEURL+"Hotels/AvailableHotels?destinationId=";
    public static String GETROOMDETAILS=BASEURL+"Hotels/HotelDetails?hotelId=";
    public static String BLOCKHOTEL=BASEURL+"Hotels/BlockHotelRoom";
    public static String BOOKHOTEL=BASEURL+"Hotels/BookHotelRoom?referenceNo=";
    public static String GETHOTELDETAILS=BASEURL+"Hotels/HotelRoomBookingDetails?referenceNo=";
    public static String CANCELHOTEL=BASEURL+"Hotels/CancelHotelRoom?referenceNo=";



    ///!******------Holidays-------********//////
    public static String BLOCKHOLIDAY=BASEURL+"HolidayPackages/BlockHolidayPackage";
    public static String BOOKHOLIDAY=BASEURL+"HolidayPackages/BookHolidayPackage?referenceNo=";
    public static String GETDESTINATIONPLACES=BASEURL+"HolidayPackages/Destinations?categoryId=";
    public static String GETCATEGORIES=BASEURL+"HolidayPackages/Categories?";
    public static String GETHOLIDAYPACKAGES=BASEURL+"HolidayPackages/AvailableHolidayPackages?";
    public static String GETHOLIDAYDETAILS=BASEURL+"HolidayPackages/HolidayPackageBookingDetails?referenceNo=";

    ///!******------Pilgrimages-------********//////
    public static String BLOCKPILGRAMAGES=BASEURL+"Pilgrimages/BlockPilgrimages";
    public static String GETCITIES=BASEURL+"Pilgrimages/Cities";
    public static String GETPILGHOTELS=BASEURL+"AvailableHotels?destinationId=";
    public static String BOOKPILGRIMAGES=BASEURL+"Pilgrimages/BookPilgrimages?referenceNo=";

    ///!******------Bill Payments-------********//////
    public static String GETOPERATORS=BASEURL+"Bills/BillsOperators?billType=";
    public static String INTIATEBILL=BASEURL+"Bills/InitiateBillPayment";
    public static String PAYBILL=BASEURL+"/Bills/Bill?referenceNo=";
    public static String BILLSTATUS= BASEURL+"Bills/BillStatus?referenceNo=";


    public static String  DeviceModel=android.os.Build.MODEL + android.os.Build.PRODUCT;
    public static String  DeviceOs=android.os.Build.VERSION.RELEASE + android.os.Build.VERSION.SDK_INT;
    public static String  DeviceOsversion=System.getProperty("os.version") + android.os.Build.VERSION.INCREMENTAL;


    public static String AGENT_CURRENCY_SYMBOL="₹ ";


    public static String USERNAME="UserName";
    public static String USEREMAIL="UserEmail";
    public static String USERPASSWORD="UserPassword";
    public static String USERPHONE="UserPhone";
    public static String USERDOB="UserDOB";
    public static String USERGENDER="UserGender";
    public static String USERID="UserId";
    public static String DEPRCYT_USER_ID="Deprct_User_Id";
    public static String USERTYPE="UserType";
    public static String RESPONSE="Response";
    public static String USERADDRESS="Address";
    public static String BALANCE="Balance";
    public static String CLIENTID="Clientid";
    public static String PARENTID="Parentid";
    public static String CRESPONSE ="C_Response";

    public static String CONTENT=BASEURL+"Accounts/DynamicPageContent";
  public static String GETBANKS="https://payment.atomtech.in/paynetz/getbanklist?merchantId=53279";




        /* DynamicPages        @"Accounts/DynamicPageContent"*/
/*public enum StaticPages
{
   /// About Client
   Aboutus = 1,
   /// Contact information of Client
   Contactus = 2,
   /// Terms and Conditions information of Client
   Termsandconditions = 3,
   /// Privacy Policy information of Client
   PrivacyPolicy = 4,
   /// Disclaimer Policy information of Client
   DisclaimerPolicy = 5,
   /// Faqs information of Client
   Faqs = 6,
   /// OurTeam information of Client
   //  OurTeam = 7,
   ///Careers information of Client
   // Careers = 8,
   ///Testimonials information of Client
   // Testimonials = 9,
   ///Testimonials information of Client
   // InvestorRelations = 10,
   ///Testimonials information of Client
   // SocialResponsibility = 11,
   // SiteMap = 12,
   // Robots = 13,
   //  Blog = 14,

}*/
}
