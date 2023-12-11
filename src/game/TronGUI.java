package game;

import canvas.TronBackground;
import canvas.TronCanvas;
import canvas.TronCountDown;
import canvas.TronWin;
import database.DatabaseException;
import motorcycle.MotorcycleColor;
import utils.Score;
import utils.ScoreBoardTableModel;
import utils.UniversalData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

public class TronGUI{
    private JFrame window;
    private JPanel mainContent;
    private TronCountDown countdown;
    private TronWin winningScreen;
    public JPanel canvasContent;
    private TronGame game;

    JTextField playerOneInput = new JTextField();
    JComboBox<String> playerOneColor = new JComboBox<String>();
    JTextField playerTwoInput = new JTextField();
    JComboBox<String> playerTwoColor = new JComboBox<String>();

    //private static Dimension windowDimension = new Dimension(600, 600);

    public TronGUI(){
        try{
            game = new TronGame(this);
        }catch (DatabaseException e){
            System.err.println("Could not initiate the database helper");
            return;
        }

        generateLogin(false, false);

    }

    public void generateLogin(boolean emptyInput, boolean incorrectColors){
        log("Generating login screen..", false);
        if(window != null){
            window.setVisible(false);
        }
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Tron Game");

        mainContent = new JPanel();
        mainContent.setPreferredSize(UniversalData.getWindowDimension());

        /*Canvas canvas = new canvas.TronCanvas();
        canvas.setPreferredSize(windowDimension);
        mainContent.add(canvas);*/

        JPanel loginpanel = new JPanel();
        //loginpanel.setPreferredSize(windowDimension);
        mainContent.setLayout(new FlowLayout(FlowLayout.CENTER));
        loginpanel.setLayout(new BoxLayout(loginpanel, BoxLayout.Y_AXIS));
        loginpanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        loginpanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        JLabel gameTitle = new JLabel("TRON GAME");
        gameTitle.setFont(new Font("Arial", Font.BOLD, 40));
        //gameTitle.setPreferredSize(new Dimension(600, 50));
        gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel loginDesc = new JLabel("For the game to start, specify the two player names and their colors:");
        loginDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] possibleColors = new String[MotorcycleColor.values.length];
        int count = 0;
        for (MotorcycleColor value : MotorcycleColor.values) {
            possibleColors[count++] = value.displayName;
        }
        JPanel inputs = new JPanel();
        inputs.setLayout(new BoxLayout(inputs, BoxLayout.Y_AXIS));
        JPanel playerOneInputs = new JPanel(new FlowLayout());

        playerOneInput = new JTextField(playerOneInput.getText());
        playerOneInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerOneInput.setPreferredSize(new Dimension(200, 30));
        playerOneColor = new JComboBox<String>(possibleColors);
        playerOneColor.setSelectedIndex(1);
        playerOneInputs.add(playerOneInput);
        playerOneInputs.add(playerOneColor);
        playerOneInputs.setPreferredSize(new Dimension(UniversalData.getWindowDimension().width, 40));

        //playerOneInput.setPreferredSize(new Dimension(400, 50));
        JPanel playerTwoInputs = new JPanel(new FlowLayout());
        playerTwoInput = new JTextField(playerTwoInput.getText());
        playerTwoInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerTwoInput.setPreferredSize(new Dimension(200, 30));
        playerTwoColor = new JComboBox<String>(possibleColors);
        playerTwoInputs.add(playerTwoInput);
        playerTwoInputs.add(playerTwoColor);
        playerTwoInputs.setPreferredSize(new Dimension(UniversalData.getWindowDimension().width, 40));

        JButton startGame = new JButton();
        startGame.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        startGame.setText("Start game!");
        startGame.setAlignmentX(Component.CENTER_ALIGNMENT);

        inputs.add(playerOneInputs);
        inputs.add(playerTwoInputs);
        inputs.setPreferredSize(new Dimension(UniversalData.getWindowDimension().width, 80));
        loginpanel.add(gameTitle);
        loginpanel.add(loginDesc);
        /*loginpanel.add(playerTwoInputs);
        loginpanel.add(playerTwoInputs);*/
        loginpanel.add(inputs);
        loginpanel.add(startGame);

        if(emptyInput){
            JLabel errorText = new JLabel("One or both of the playernames were empty.");
            errorText.setFont(new Font("Arial", Font.PLAIN, 15));
            errorText.setOpaque(true);
            errorText.setBackground(new Color(255, 200, 200));
            errorText.setAlignmentX(Component.CENTER_ALIGNMENT);
            loginpanel.add(errorText);
        }else if(incorrectColors){
            JLabel errorText = new JLabel("The two selected colors cannot be the same");
            errorText.setFont(new Font("Arial", Font.PLAIN, 15));
            errorText.setOpaque(true);
            errorText.setBackground(new Color(255, 200, 200));
            errorText.setAlignmentX(Component.CENTER_ALIGNMENT);
            loginpanel.add(errorText);
        }



        JPanel scoresPanel = new JPanel();
        scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.Y_AXIS));
        scoresPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        JLabel scoreTitle = new JLabel("Scoreboard");
        scoreTitle.setFont(new Font("Arial", Font.BOLD, 25));
        scoreTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoresPanel.add(scoreTitle);

        List<Score> scores = game.getScoreBoardData();
        JTable scoreBoard = new JTable(new ScoreBoardTableModel(scores));
        scoreBoard.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreBoard.setRowHeight(30);
        scoreBoard.setRowSelectionAllowed(false);
        scoreBoard.setShowHorizontalLines(true);
        scoreBoard.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        JScrollPane scrollPane = new JScrollPane(scoreBoard);
        scoreBoard.setFillsViewportHeight(true);
        scoresPanel.setBorder(new EmptyBorder(50, 0, 0, 0));
        scoresPanel.add(scrollPane);
        loginpanel.add(scoresPanel);

        mainContent.add(loginpanel);
        window.add(mainContent);
        window.setPreferredSize(UniversalData.getWindowDimension());

        window.pack();
        window.setVisible(true);
    }

    private void startGame(){
        if(Objects.equals(playerOneInput.getText(), "") || Objects.equals(playerTwoInput.getText(), "")){
            log("One or both of the player's names were empty. Re-displaying the login screen", true);
            generateLogin(true, false);
            return;
        }
        if(Objects.equals(playerOneColor.getSelectedItem(), playerTwoColor.getSelectedItem())){
            // same color selected
            log("The two selected colors cannot be the same", true);
            generateLogin(false, true);
            return;
        }
        log("Two names specified, launching game...", false);
        game.launchGame(playerOneInput.getText(), playerTwoInput.getText(), MotorcycleColor.getColorByName((String) playerOneColor.getSelectedItem()), MotorcycleColor.getColorByName((String) playerTwoColor.getSelectedItem()));
    }
    public void generateGameSpace(TronCanvas canv, TronBackground canvBack, Runnable gameLaunchLogicFunction, TronGame game) {
        log("Generating game space..", false);
        if(window != null){
            window.setVisible(false);
        }
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Tron Game");
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //log("Key pressed: " + e.getKeyChar() + "[" + e.getKeyCode() + "]", false);
                game.handleKeyPress(e.getKeyCode());
            }
        });
        mainContent = new JPanel();
        mainContent.setPreferredSize(UniversalData.getWindowDimension());

        canvasContent = new JPanel();
        canvasContent.setLayout(new OverlayLayout(canvasContent));
        canvasContent.setPreferredSize(UniversalData.getWindowDimension());


        log("Rendering background..", false);
        JPanel canvasBackground = canvBack;
        canvasBackground.setPreferredSize(UniversalData.getWindowDimension());
        log("Rendering game canvas..", false);
        JPanel canvas = canv;
        canvas.setPreferredSize(UniversalData.getWindowDimension());
        canvas.setOpaque(true);
        /*canvasContent.add(canvasBackground, new Integer(1));
        canvasContent.add(canvas, new Integer(2));*/

        countdown = new TronCountDown();
        countdown.setPreferredSize(UniversalData.getWindowDimension());
        countdown.setOpaque(true);

        winningScreen = new TronWin();
        winningScreen.setPreferredSize(UniversalData.getWindowDimension());
        winningScreen.setOpaque(true);
        winningScreen.setVisible(false);

        canvasContent.add(winningScreen);
        canvasContent.add(countdown);
        canvasContent.add(canvas);
        canvasContent.add(canvasBackground);


        mainContent.add(canvasContent);
        window.add(mainContent);
        window.setPreferredSize(UniversalData.getWindowDimension());

        window.pack();
        window.setVisible(true);
        startCounting(gameLaunchLogicFunction);
        log("Starting the countdown...", false);
    }

    public void generateWinningScreen(String winningPlayer){
        if(Objects.equals(winningPlayer, null)){
            // draw
            winningScreen.setDraw(true);
            winningScreen.setVisible(true);
            canvasContent.repaint();
        }else{
            winningScreen.setWinningPlayer(winningPlayer);
            winningScreen.setVisible(true);
            canvasContent.repaint();
        }
    }

    public void startCounting(Runnable func){
        setTimeout(() -> {
            if(countdown.count-1 == 0){
                // vege a countdownnak
                countdown.setVisible(false);
                func.run();
            }else{
                countdown.setCount(countdown.count-1);
                canvasContent.repaint();

                startCounting(func);
            }

        }, 1000);
    }
    private void log(String msg, boolean error){
        if(error){
            System.err.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][GUI][ERROR] " + msg);
        }else{
            System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][GUI] " + msg);
        }

    }

    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
}
