public class App {
  public static void main(String[] args) throws Exception {
    args = new String[] {
      "C:\\repos\\availity-assessment\\assessments\\batch-file-processor\\test.csv"
    };
    String batchFilePath = getCommandLineArgument(args);

    BatchFileProcessor processor = new BatchFileProcessor();
    processor.process(batchFilePath);
  }

  private static String getCommandLineArgument(String[] args) throws Exception {
    if(args.length == 0) {
      throw new Exception("Missing batch file path argument");
    }

    return args[0];
  }
}
