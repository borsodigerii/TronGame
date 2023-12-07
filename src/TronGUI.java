import canvas.TronCanvas;
import database.DatabaseException;
import utils.UniversalData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class TronGUI{
    private JFrame window;
    private JPanel mainContent;
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
            System.err.println("[Login] One or both of the player's names were empty. Re-displaying the login screen");
            generateLogin(true);
            return;
        }
        System.out.println("[Login] Two names specified, launching game...");
        game.launchGame();
    }

    public void generateGameSpace(TronCanvas canv) {
        if(window != null){
            window.setVisible(false);
        }
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Tron Game");

        mainContent = new JPanel();
        mainContent.setPreferredSize(UniversalData.getWindowDimension());
        // TODO: implement background paint as well
        Canvas canvas = canv;
        canvas.setPreferredSize(UniversalData.getWindowDimension());
        mainContent.add(canvas);
        window.add(mainContent);
        window.setPreferredSize(UniversalData.getWindowDimension());

        window.pack();
        window.setVisible(true);
    }
}
