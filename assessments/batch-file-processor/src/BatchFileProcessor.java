import java.io.* ;
import java.util.*;

public class BatchFileProcessor {
  public BatchFileProcessor() {}

  public void process(String batchFilePath) throws Exception {
    String line = "";
    
    int lastSlashIndex = batchFilePath.lastIndexOf("\\");
    String outputPath = batchFilePath.substring(0, lastSlashIndex);

    HashMap<String, ArrayList<BatchFileRow>> insuranceCompanyList = new HashMap<String, ArrayList<BatchFileRow>>();
    FileReader batchFileReader = new FileReader(batchFilePath);

    try (BufferedReader bufferedReader = new BufferedReader(batchFileReader)) {
      int rowNumber = 1;
      while ((line = bufferedReader.readLine()) != null) {
        addRowToList(insuranceCompanyList, line, rowNumber);
        rowNumber++;
      }

      processIntoFiles(insuranceCompanyList, outputPath);
    }
  }

  private void addRowToList(HashMap<String, ArrayList<BatchFileRow>> insuranceCompanyList, String line, int rowNumber) throws Exception {
    String delimitter = ",";
    String[] splitLine = line.split(delimitter);

    if(splitLine.length != 5) {
      throw new Exception(
        String.format(
          "Invalid number of columns on row %s. Expected 5 columns but %s were found.", 
          rowNumber, 
          splitLine.length
        )
      );
    }

    BatchFileRow row = createNewRow(splitLine);

    String insuranceName = row.insuranceCompany.replaceAll("\\s", "");

    if(!insuranceCompanyList.containsKey(insuranceName)) {
      ArrayList<BatchFileRow> newRowList = new ArrayList<BatchFileRow>();
      newRowList.add(row);
      insuranceCompanyList.put(insuranceName, newRowList);
    } else {
      ArrayList<BatchFileRow> rowList = insuranceCompanyList.get(insuranceName);
      rowList = addNewRowToRowList(rowList, row);
      insuranceCompanyList.put(insuranceName, rowList);
    }
  }

  private BatchFileRow createNewRow(String[] splitLine) {
    BatchFileRow row = new BatchFileRow();
    row.userId = splitLine[0];
    row.firstName = splitLine[1];
    row.lastName = splitLine[2];
    row.version = Integer.parseInt(splitLine[3]);
    row.insuranceCompany = splitLine[4];

    return row;
  }

  private ArrayList<BatchFileRow> addNewRowToRowList(ArrayList<BatchFileRow> rowList, BatchFileRow row) throws Exception {
    for(int i=0; i<rowList.size(); i++) {
      BatchFileRow currentRow = rowList.get(i);

      if(currentRow.userId.equals(row.userId)) {
        BatchFileRow uniqueRow = row.version >= currentRow.version ? row : currentRow;
        rowList.set(i, uniqueRow);
        return rowList;
      }
    }

    rowList.add(row);

    return rowList;
  }

  private void processIntoFiles(HashMap<String, ArrayList<BatchFileRow>> insuranceCompanyList, String outputPath) throws Exception {
    Set<String> allCompaniesSet = insuranceCompanyList.keySet();
    Iterator<String> allCompanies = allCompaniesSet.iterator();

    while(allCompanies.hasNext()) {
      String insuranceName = allCompanies.next();
      ArrayList<BatchFileRow> rowList = insuranceCompanyList.get(insuranceName);

      rowList.sort((o1, o2) -> (o1.lastName + o1.firstName).compareTo((o2.lastName + o2.firstName)));

      String currentFilePath = outputPath + "\\" + insuranceName + ".csv";
      BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilePath));

      for(int i=0; i<rowList.size(); i++) {
        BatchFileRow currentRow = rowList.get(i);

        String fileRow = String.format(
          "%s,%s,%s\r\n", 
          currentRow.userId,
          currentRow.firstName,
          currentRow.lastName
        );

        writer.append(fileRow);

        printCurrentRow(insuranceName, currentRow);
      }

      writer.close();
    }
  }

  private void printCurrentRow(String insuranceName, BatchFileRow currentRow) {
    System.out.println(
      String.format(
        "Ins: %s [userId: %s | firstName: %s | lastName: %s | version: %d | insuranceCompany: %s]", 
        insuranceName,
        currentRow.userId,
        currentRow.firstName,
        currentRow.lastName,
        currentRow.version,
        currentRow.insuranceCompany
      )
    );
  }
}
