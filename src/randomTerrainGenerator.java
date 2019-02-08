import java.io.PrintWriter;

public class randomTerrainGenerator {
    private double [][] terrain;
    private double [][] newArray;
    private int lb;
    private int ub;

    public randomTerrainGenerator(int l, int u){
        terrain = new double[255][255];
        lb = l;
        ub = u;
    }

    private void randomFill(double[][] table) {
        newArray = new double[table.length][table[0].length];
        for (int i = 0; i < table.length; i++) {

            for (int j = 0; j < table[0].length; j++) {
                table[i][j] = (Math.random() * 100);
            }
        }
    }

    private void average() {

        for (int i = 1; i < terrain.length - 1; i++) {

            for (int j = 1; j < terrain[0].length - 1; j++) {
                newArray[i][j] = (terrain[i+1][j] + terrain[i-1][j] + terrain[i][j+1] + terrain[i][j-1])/4;
            }
        }
    }

    private void copyOldArray() {

        for (int i = 0; i < terrain.length; i++) {

            for (int j = 0; j < terrain[0].length; j++) {
                newArray[i][j] = terrain[i][j];
            }
        }
    }

    public String toString() {
        double [][] leachArray = terrain;
        StringBuilder ss = new StringBuilder();
        for (int i = 10; i < leachArray.length - 10; i++) {
            for (int j = 10; j < leachArray[i].length - 10; j++) {
                ss.append(String.valueOf(leachArray[i][j]));
                ss.append(", ");
            }
            ss.delete(ss.length() - 2, ss.length());
            ss.append("\n");
        }
        ss.delete(ss.length() - 1, ss.length());
        return ss.toString();
    }

    public static void writeToFile(String toWrite, String fileName) {
        try {
            PrintWriter pw = new PrintWriter(fileName);
            pw.write(toWrite);
        }
        catch (Exception e) {
            System.out.println("File unable to be opened");
        }
    }

    private void copyNewArray() {

        for (int i = 0; i < terrain.length; i++) {

            for (int j = 0; j < terrain[0].length; j++) {
                terrain[i][j] = newArray[i][j];
            }
        }
    }


    public double[][] generateTerrain() {
        double maxChange = 4;
        randomFill(terrain);
        copyOldArray();

        while (maxChange > 3) {
            average();
            maxChange = getChange();
            copyNewArray();
        }
        return newArray;
    }

    private double getChange(){
        double c = 0;
        for (int i = 0; i < newArray.length; i++) {

            for (int j = 0; j < newArray[0].length; j++) {

                if (Math.abs(newArray[i][j] - terrain[i][j]) > c) {

                    c = Math.abs(newArray[i][j] - terrain[i][j]);
                }
            }
        }
        return c;
    }

    public static void main(String [] args){
        randomTerrainGenerator thisIsMyLand = new randomTerrainGenerator(0, 500);
        thisIsMyLand.generateTerrain();
        System.out.println(thisIsMyLand.toString());
        writeToFile(thisIsMyLand.toString(), "myfile.csv");
    }
}
