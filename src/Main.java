import java.io.*;
import java.nio.file.FileSystemException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {

        collectionOfInformation();

    }

    public static void collectionOfInformation() throws Exception{
        System.out.println("Введите фамилию, имя, отчество, дату рождения (в формате dd.mm.yyyy), " +
                "номер телефона (число без разделителей) и пол(символ латиницей f или m), разделенные пробелом");

        String txt;
        try(BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            txt = bf.readLine();
        }catch (IOException e){
            throw new Exception("Произошла ошибка при работе с консолью");
        }

        String[] arr = txt.split(" ");
        if (arr.length != 6){
            throw new Exception("Введено неверное количество параметров");
        }

        String surname = arr[0];
        String name = arr[1];
        String patronymic = arr[2];

        SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        Date dateOfBirth;
        try {
            dateOfBirth = format.parse(arr[3]);
        }catch (ParseException e){
            throw new ParseException("Неверный формат даты рождения", e.getErrorOffset());
        }

        int numPhone;
        try {
            numPhone = Integer.parseInt(arr[4]);
        }catch (NumberFormatException e){
            throw new NumberFormatException("Неверный формат телефона");
        }

        String sex = arr[5];
        if (!sex.toLowerCase().equals("m") && !sex.toLowerCase().equals("f")){
            throw new RuntimeException("Неверно введен пол");
        }

        String fileName =  surname.toLowerCase() + ".txt";
        File file = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(file, true)){
            if (file.length() > 0){
                fileWriter.write('\n');
            }
            fileWriter.write(String.format("%s %s %s %s %s %s", surname, name, patronymic, format.format(dateOfBirth), numPhone, sex));
        }catch (IOException e){
            throw new FileSystemException("Возникла ошибка при работе с файлом");
        }

    }
}