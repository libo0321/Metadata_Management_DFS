package Basic;

public class File extends BasicClass{
    protected boolean canRead = true;
    protected boolean canWrite = true;
    protected boolean canExecute = true;

    public File(String absolute_path) {
        super(absolute_path, Type.File);
    }
    public File(String current_path, String name) {super(current_path+name, Type.File);}


    /* Accesseurs */
    public String getMod(){
        String Mod = "-";
        if (canRead)
            Mod += "r";
        if (canWrite)
            Mod += 'w';
        if(canExecute)
            Mod += 'x';
        return Mod;
    }

    /* Mutateurs */
    public void chmod(String Mod){
        canRead = Mod.contains("r");
        canWrite = Mod.contains("w");
        canExecute = Mod.contains("x");
    }

    @Override
    public String toString() {
        return "    type: "+type.toString()+
                "    permission: "+getMod();
    }

}
