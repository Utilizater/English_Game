public class WordModel {

    private String englishWord;

    private String russianWord;

    private int id;

    private int block_number;

    public WordModel(String englishWord, String russianWord) {

        this.englishWord = englishWord;

        this.russianWord = russianWord;


    }

    public WordModel(String englishWord, String russianWord, int block_number) {

        this.englishWord = englishWord;

        this.russianWord = russianWord;

        this.block_number = block_number;

        id = 0;

    }

////////////

    public String getEnglishWord() {

        return englishWord;

    }

    public int getBlock_number ()
    {
        return block_number;
    }

    public void setBlock_number(int block_number)
    {
        this.block_number = block_number;
    }

    public void setEnglishWord(String englishWord) {

        this.englishWord = englishWord;

    }

/////////////

    public void setid(byte id) {

        this.id = id;

    }

    public int getid() {

        return id;

    }

//////////////

    public String getrussianWord() {

        return russianWord;

    }

    public void setRussianWord(String russianWord) {

        this.russianWord = russianWord;

    }

///////

}