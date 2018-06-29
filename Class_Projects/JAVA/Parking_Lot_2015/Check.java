import java.util.Date;

public class Check{
    Date date = new Date();

    String checkin = "";
    String checkout = "";
    String cname = "";
    String carnum = "";
    String carbrand = "";
    String carcolor = "";
    String carsize = "";
    String parkingp = "";
    double charge = 0;

    public long checkIn = System.currentTimeMillis();
    public long checkOut = System.currentTimeMillis();

    public void setNum(String a){
	this.parkingp = a;
    }
    public void setName(String a){
	this.cname = a;
    }
    public void setCnum(String a){
	this.carnum = a;
    }
    public void setCbrand(String a){
	this.carbrand = a;
    }
    public void setCcolor(String a){
	this.carcolor = a;
    }
    public void setIn(String a){
	this.checkin = a;
    }
    public void setOut(String a){
	this.checkout = a;
    }
    public void setCsize(String a){
	this.carsize = a;
    }
    public void setC(double a){
	this.charge = a;
    }

    public String getNum(){
	return parkingp;
    }
    public String getName(){
	return cname;
    }
    public String getCnum(){
	return carnum;
    }
    public String getCbrand(){
	return carbrand;
    }
    public String getCcolor(){
	return carcolor;
    }
    public String getIn(){
	return checkin;
    }
    public String getOut(){
	return checkout;
    }
    public String getSize(){
	return carsize;
    }
    public double getCharge(){
	return charge;
    }

}