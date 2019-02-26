package sk.tuke;

public class StringConnector {

    private StringBuilder partialResults[];

    public StringConnector(int threadCount){
        partialResults = new StringBuilder[threadCount];
        initialize(threadCount);
    }

    private void initialize(int threadCount) {
        for(int i = 0; i < threadCount; i++){
            partialResults[i] = new StringBuilder();
        }
    }

    public StringBuilder[] getPartialResults() {
        return partialResults;
    }
}
