package MaxClique;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

public class Main {

    public static void main(String [] args){
        try {
            InputHandler ih = new InputHandler();
        } catch (FileNotFoundException e) {
            System.out.println("Graph file doesn't exist.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("I/O is fucked.");
            System.exit(0);
        }
        Operation.PointArray = InputHandler.PointArray.clone();

//        Integer a[] = {3,4,5,6,7,8,9};
//        HashSet<Integer> set = new HashSet<>();
//        set.addAll(Arrays.asList(a));
//        MaxClique maxClique = new MaxClique(set);
//        System.out.print(maxClique.maxClique());

        HashSet<Integer> clique = Trygetmore.getMore();
        System.out.println("answer:"+clique.size());
        System.out.println(new Date(System.currentTimeMillis()));

        File output = new File("output.txt");
        try {
			FileWriter fWriter = new FileWriter(output);
			Iterator<Integer> iterator = clique.iterator();
			Integer i;
			while(iterator.hasNext()){
				i = iterator.next();
				fWriter.write(i+" ");
			}
			fWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }

}
