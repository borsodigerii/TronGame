package game;

import canvas.TronBackground;
import canvas.TronCanvas;
import canvas.TronCountDown;
import canvas.TronWin;
import database.DatabaseException;
import utils.UniversalData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class TronGUI{
    private JFrame window;
    private JPanel mainContent;
    private TronCountDown countdown;
    private TronWin winningScreen;
    public JPanel canvasContent;
    private TronGame game;

    JTextField playerOneInput = new JTextField();
    JTextField playerTwoInput = new JTextField();

    //private static Dimension windowDimension = new Dimension(600, 600);

    public TronGUI(){
        try{
            game = new TronGame(this);
        }catch (DatabaseException e){
            System.err.println("Could not initiate the database helper");
            return;
        }

        generateLogin(false);

    }

    private void generateLogin(boolean emptyInput){
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
        JLabel gameTitle = new JLabel("TRON GAME");
        gameTitle.setFont(new Font("Arial", Font.BOLD, 20));
        //gameTitle.setPreferredSize(new Dimension(600, 50));
        gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel loginDesc = new JLabel("For the game to start, specify the two player names:");
        loginDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerOneInput = new JTextField();
        playerOneInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        //playerOneInput.setPreferredSize(new Dimension(400, 50));
        playerTwoInput = new JTextField();
        playerTwoInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton startGame = new JButton();
        startGame.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        startGame.setText("Start game!");
        startGame.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginpanel.add(gameTitle);
        loginpanel.add(loginDesc);
        loginpanel.add(playerOneInput);
        loginpanel.add(playerTwoInput);
        loginpanel.add(startGame);

        if(emptyInput){
            JLabel errorText = new JLabel("One or both of the playernames were empty.");
            errorText.setFont(new Font("Arial", Font.PLAIN, 15));
            errorText.setOpaque(true);
            errorText.setBackground(new Color(255, 200, 200));
            errorText.setAlignmentX(Component.CENTER_ALIGNMENT);
            loginpanel.add(errorText);
        }
        mainContent.add(loginpanel);
        window.add(mainContent);
        window.setPreferredSize(UniversalData.getWindowDimension());

        window.pack();
        window.setVisible(true);
    }

    private void startGame(){
        if(Objects.equals(playerOneInput.getText(), "") || Objects.equals(playerTwoInput.getText(), "")){
            log("One or both of the player's names were empty. Re-displaying the login screen", true);
            generateLogin(true);
            return;
        }
        log("Two names specified, launching game...", false);
        game.launchGame(playerOneInput.getText(), playerTwoInput.getText());
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
        //TODO: befejezni
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
