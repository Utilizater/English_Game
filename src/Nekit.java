import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


/*
*
    * There are several behaviors
*    1: classic
*    2: random list
* */


class Nekit extends JFrame {

    HashMap<String, Integer> ReportMap = new HashMap<>();

    LinkedList<WordModel> list = new LinkedList<WordModel>(); // основной масив

    JTextArea countLabel1 = new JTextArea(); //окно для ввода


    JLabel okno1 = new JLabel();

    int block_number;

    String text_file;

    byte regim = 0; // режим состояния окна 1 - сравниваем; 2 - переходим в

// первый режим; 0 - только зашли

    int kursor;

    int behavior;

    Nekit(String text_file, int block_number, int behavior) {

        this.text_file = text_file;
        this.block_number = block_number;
        this.behavior = behavior;


        switch (behavior)
        {
            case 1:
                list = GetListRegimOne(block_number, GetFullList());
                break;

            case 2:
                list = GetRandomList(30, GetFullList());
                break;

        }

        okno1.setFont(new Font("Serif", Font.PLAIN, 30));
        countLabel1.setFont(new Font("Serif", Font.PLAIN, 30));

// System.out.print("3");

        JPanel Panel1 = new JPanel(new GridLayout(3, 12, 80, 80));

        setBounds(10, 10, 500, 500);

        add(Panel1, BorderLayout.CENTER);

        JButton knopka1 = new JButton("Ok");

        Panel1.add(countLabel1);

        Panel1.add(okno1);

        Panel1.add(knopka1);

        setVisible(true);


        countLabel1.addKeyListener(new KeyListener() {

            @Override

            public void keyPressed(KeyEvent e){

                if(e.getKeyCode() == KeyEvent.VK_ENTER){

                    e.consume();

                    buttoncklick();
                }
            }

            @Override

            public void keyTyped(KeyEvent e){}

            @Override

            public void keyReleased(KeyEvent e){}

        });

        knopka1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                buttoncklick();
            }
        }
        );

    }


    int variant(LinkedList list) {

       // new Random().nextInt(5);

        return new Random().nextInt(list.size());

    }

    void buttoncklick() {    // System.exit(0);

        switch (regim) {

            case 0:

//	System.out.println("Режим 0");

                countLabel1.setText("");

                okno1.setText("");

                kursor = variant(list);

//if (list.get(kursor) == null) {System.exit(0);}

                okno1.setText(list.get(kursor).getrussianWord());

                regim = 1;

                break;

            case 1:

//	System.out.println("Режим 1");

                String first = list.get(kursor).getEnglishWord();

                String second = countLabel1.getText();

                if (first.equalsIgnoreCase(second)) {

                    okno1.setText("Молодэц!");

                    int i = list.size(); ////////////////////////////////////////////////////////////////

                    String count = String.valueOf(i);
                    this.setTitle(count);

                    switch (list.get(kursor).getid()) {

                        case 0:

                            list.remove(kursor);

                            int j = list.size(); ////////////////////////////////////////////////////////////////

                            String count2 = String.valueOf(j);
                            this.setTitle(count2);
                          //  System.out.println(list.size());

                            break;

                        case 3:

                            list.get(kursor).setid((byte) 2);

                            break;

                        case 2:

                            list.get(kursor).setid((byte) 1);

                            break;

                        case 1:

                            list.get(kursor).setid((byte) 0);

                            break;

                    }

                } else {

                    okno1.setText("Правильно будет не "

                            + countLabel1.getText() + ", а "

                            + list.get(kursor).getEnglishWord());

                    list.get(kursor).setid((byte) 3);

                    ////////   set to report
                    if (!ReportMap.containsKey(list.get(kursor).getEnglishWord()))
                    {
                        ReportMap.put(list.get(kursor).getEnglishWord(), 1);
                       // System.out.println("Added word");
                    }
                    else
                    {
                      //  System.out.println("Повторно - " + list.get(kursor).getEnglishWord());
                        ReportMap.put(list.get(kursor).getEnglishWord(), ReportMap.get(list.get(kursor).getEnglishWord())+1);
                    }

//okno1.setText("Ошибка");

                }

                regim = 2;

                break;

            case 2:

//	System.out.println("Режим 2");

                countLabel1.setText("");

                if (list.size() == 0) {

                    ReportPrint(ReportMap);
                    System.exit(0);

                }

                kursor = variant(list);

                okno1.setText(list.get(kursor).getrussianWord());

                regim = 1;

                break;

            default:


                System.exit(1);

                break;

        }
    }
    LinkedList<WordModel> GetFullList()
    {
        String sCurrentLine = null;

        LinkedList<WordModel> list = new LinkedList<WordModel>(); // сюда херачим слова

        String[] tokens;

        try {

            BufferedReader br = new BufferedReader(new FileReader(text_file));

            while ((sCurrentLine = br.readLine()) != null) {
               // System.out.println(sCurrentLine);
                tokens = sCurrentLine.split(":");
                list.add(new WordModel(tokens[0], tokens[1], Integer.parseInt(tokens[2])));

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //System.out.println("list size is "+list.size());
        return list;

    }

    LinkedList<WordModel> GetListRegimOne(int block_number, LinkedList<WordModel> full_list)
    {

        for (int i = 0; i < full_list.size(); i++)
        {
            if(full_list.get(i).getBlock_number() != block_number)
            {
                full_list.remove(i);
                i--;
            }
        }

        return full_list;
    }

    LinkedList<WordModel> GetRandomList(int size_list, LinkedList<WordModel> full_list)
    {
        LinkedList<WordModel> GetRandomList = new LinkedList<>();
        LinkedList<Integer> TempLsist = new LinkedList<>();

        for (int i = 0; i < full_list.size(); i++)
        {
            TempLsist.add(i);
        }

        for (int i = 0; i < size_list; i++)
        {
           int cursor = new Random().nextInt(TempLsist.size());
           GetRandomList.add(full_list.get(TempLsist.get(cursor)));
           TempLsist.remove(cursor);
        }

        return GetRandomList;
    }
    void ReportPrint(HashMap<String, Integer> map)
    {
        System.out.println("-----------//Report//--------------");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key+" - "+value);
        }
    }
}
