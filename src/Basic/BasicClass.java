package Basic;

public abstract class BasicClass {
    protected int id;
    protected String path;
    protected Type type;

    protected enum Type {
        File, Folder
    }

    private static int number = 0;

    public BasicClass(String path, Type type){
        this.id = number;
        this.path = path;
        this.type = type;
        number++;
    }

    /* Accesseurs */
    public int getId() {return id;}
    public String getPath() {return path;}
    public Type getType() {return type;}

    @Override
    public abstract String toString();
}
