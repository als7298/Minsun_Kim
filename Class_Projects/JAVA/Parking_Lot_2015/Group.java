public class Group{

    private String name;
    private String carn;
    private int num;

    public Group(){
    }

    public Group(String a, String b, int c){
	name = a;
	carn = b;
	num = c;
    }

    public String getName(){
	return name;
    }
    public String getCarn(){
	return carn;
    }
    public int getNum(){
	return num;
    }

    public void setName(){
	this.name = name;
    }
    public void setCarn(){
	this.carn = carn;
    }
    public void setNum(){
	this.num = num;
    }
}