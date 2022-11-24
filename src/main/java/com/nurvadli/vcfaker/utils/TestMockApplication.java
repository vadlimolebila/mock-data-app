package com.nurvadli.vcfaker.utils;

import com.github.javafaker.Faker;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class TestMockApplication {

    public static void main(String[] args) throws Exception {

        System.out.println("Name: "+FakerUtil.getName());
        System.out.println("Mobile phone: "+FakerUtil.getMobilePhone());
        System.out.println("Email: "+FakerUtil.getEmail());
        System.out.println("Email: "+FakerUtil.getAddress());

        generateExcelFile();
    }

    private static String formatDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    private static String toStringAmount(double amount) {
        return String.valueOf(amount).replaceAll(".0","");
    }

    private static String[] provinceList(){
        return new String[] {
                "Nanggroe Aceh Darussalam",
                "Sumatera Utara" ,
                "Sumatera Selatan" ,
                "Sumatera Barat" ,
                "Bengkulu" ,
                "Riau" ,
                "Kepulauan" ,
                "Jambi" ,
                "Lampung" ,
                "Bangka Belitung" ,
                "Kalimantan Barat" ,
                "Kalimantan Timur",
                "Kalimantan Selatan" ,
                "Kalimantan Tengah" ,
                "Kalimantan Utara" ,
                "Banten" ,
                "DKI Jakarta",
                "Jawa Barat" ,
                "Jawa Tengah",
                "Daerah Istimewa Yogyakarta" ,
                "Jawa Timur" ,
                "Bali" ,
                "Nusa Tenggara Timur" ,
                "Nusa Tenggara Barat" ,
                "Gorontalo" ,
                "Sulawesi Barat" ,
                "Sulawesi Tengah" ,
                "Sulawesi Utara",
                "Sulawesi Tenggara" ,
                "Sulawesi Selatan" ,
                "Maluku Utara" ,
                "Maluku" ,
                "Papua Barat" ,
                "Papua" ,
                "Papua Tengah" ,
                "Papua Pegunungan" ,
                "Papua Selatan",
                "Papua Barat Daya"
        };
    }

    public static void generateExcelFile() throws Exception {
        Integer numbersOfRows = 5;
        String[] genderOptions = new String[] {"Pria", "Wanita", "Laki-laki","Perempuan","Male", "Female"};
        String[] religionOptions = new String[] {"Islam", "Kristen", "Katolik","Hindu","Budha", "Konghucu"};
        String[] educationOptions = new String[] {"S1", "S2", "S3","D1","D2", "D3"};
        String[] marriageOptions = new String[] {"Menikah", "Belum Menikah"};
        String[] bankOptions = new String[] {"BCA", "Mandiri", "BRI", "OCBC", "Bank DKI"};

        // workbook object
        XSSFWorkbook workbook = new XSSFWorkbook();

        // spreadsheet object
        XSSFSheet spreadsheet = workbook.createSheet(" Student Data ");

        // creating a row object
        XSSFRow row;

        // This data needs to be written (Object[])
        Map<String, Object[]> employeeData = new TreeMap<String, Object[]>();

        employeeData.put( "1", new Object[] {"Date of file","Employee ID Number","Mobile phone","Employee name","Corporate Employee Email",
                "Place of birth","Date of birth","Gender","Religion","Education","Marriage status",
                "Province (KTP document)","City (KTP document)","Full address (KTP document)","KTP Number (KTP document)",
                "Employer/Company name","Current Job position"	,"Start working date" ,	"City (workplace)",
                "Date of Salary","Employee Income Current (Net Salary)","Bank Name","Bank_account_number"});

        String domainName = FakerUtil.init().internet().domainName();
        String companyName = FakerUtil.init().company().name();
        for(int i=1; i<= numbersOfRows; i++) {
            Faker f = FakerUtil.init();

            employeeData.put(String.valueOf(i+1), new Object[]{
                            "2022-11-24", f.idNumber().ssnValid(), f.phoneNumber().phoneNumber(), f.name().fullName(), f.bothify("????##@"+domainName),
                            f.address().cityName(), formatDate(f.date().birthday(20, 30)), f.options().option(genderOptions), f.options().option(educationOptions),
                    f.options().option(religionOptions), f.options().option(marriageOptions), f.options().option(provinceList()), f.address().cityName(),
                    f.address().fullAddress(), String.valueOf(f.number().randomNumber(16,true)), companyName, f.job().position(), formatDate(f.date().past(260, TimeUnit.DAYS)),
                    f.address().cityName(), String.valueOf(f.number().numberBetween(1,25)), toStringAmount(f.number().randomDouble(0, 5000000, 10000000)),
                    f.options().option(bankOptions), f.business().creditCardNumber().replaceAll("-","")});
        }

        Set<String> keyid = employeeData.keySet();

        int rowid = 0;

        // writing the data into the sheets...

        for (String key : keyid) {

            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = employeeData.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
        }

        // .xlsx is the format for Excel Sheets...
        // writing the workbook into the file...
        FileOutputStream out = new FileOutputStream(  new File("C:/var/"+companyName+".xlsx"));

        workbook.write(out);
        out.close();


    }
}
