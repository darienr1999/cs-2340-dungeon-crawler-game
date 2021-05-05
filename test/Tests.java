package test;

import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.Scenes;
import org.junit.Test;

import org.testfx.framework.junit.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;

import java.awt.*;
import java.awt.event.KeyEvent;

import java.util.LinkedList;

import game.*;

import main.Main;
import org.testfx.matcher.base.NodeMatchers;




public class Tests extends ApplicationTest {

    public void start(Stage primaryStage) throws Exception {
        Main main = new Main();
        main.start(primaryStage);
        //System.out.println(primaryStage.getScene());
    }

    //This test checks if everything is there for main menu Scene
    @Test
    public void testMenuScene() {
        verifyThat("Dungeon Crawler", NodeMatchers.isNotNull());
        verifyThat("Click to enter the Dungeon", NodeMatchers.isNotNull());
        verifyThat("Start", NodeMatchers.isNotNull());
    }

    // This test confirms that everything is there for config scene
    @Test
    public void testConfigScene() {
        clickOn("Start");
        verifyThat("Enter Name: ", NodeMatchers.isNotNull());
        verifyThat("Select Difficulty", NodeMatchers.isNotNull());
        verifyThat("Select Starting weapon", NodeMatchers.isNotNull());

        System.out.println("started");
    }

    //This test confirms that if you dont enter a name,
    // a message telling you to enter a name will appear
    @Test
    public void nullNameSpaceTest() {
        clickOn("Start");
        clickOn("Confirm");
        verifyThat("Please Enter a Valid Name", NodeMatchers.isNotNull());
    }

    /*
    This test confirms you have entered a name
    confirms that the money was calculated correctly
    and makes sure the difficulty was on the right setting
     */
    @Test
    public void testNameandMoney() {
        clickOn("Start");
        write("Yo Mama");
        //This clicks on the drop down for difficulty
        clickOn("Medium");
        //This second one picks the actual difficulty you want
        clickOn("lmao");
        clickOn("Confirm");
        verifyThat("Money: ", NodeMatchers.isNotNull());
        //The money value is calculated by 500 * the difficulty multiplier which is
        /*
        1 for lmao
        2 for hard
        3 for medium
        4 for easy
        ex: easy mode money = 500 * 4 = 2000
         */
        int moneyValue = 500;
        assertEquals(Main.getMoney(), moneyValue);
        //Check to see if right difficulty
        assertEquals(Main.getDifficulty(), "lmao");
    }

    //This test confirms that if you enter a name with only whitespace,
    // a message telling you to enter a name will appear
    @Test
    public void whitespaceNameSpaceTest() {
        clickOn("Start");
        write("       ");
        clickOn("Confirm");
        verifyThat("Please Enter a Valid Name", NodeMatchers.isNotNull());
    }

    //This test if you did not enter a name and clicked confirm
    @Test
    public void emptyNameTest() {
        clickOn("Start");
        write("");
        clickOn("Confirm");
        verifyThat("Please Enter a Valid Name", NodeMatchers.isNotNull());
    }

    /*
    This test confirms that the money was calculated correctly for the Hard difficulty
    and makes sure the difficulty was on the right setting
     */
    @Test
    public void testCorrectMoneyHard() {
        clickOn("Start");
        write("Yo Mama");
        //This clicks on the drop down for difficulty
        clickOn("Medium");
        //This second one picks the actual difficulty you want
        clickOn("Hard");
        clickOn("Confirm");
        verifyThat("Money: ", NodeMatchers.isNotNull());
        //The money value is calculated by 500 * the difficulty multiplier which is
        /*
        1 for lmao
        2 for hard
        3 for medium
        4 for easy
        ex: easy mode money = 500 * 4 = 2000
         */
        int moneyValue = 500 * 2;
        assertEquals(Main.getMoney(), moneyValue);
        //Check to see if right difficulty
        assertEquals(Main.getDifficulty(), "Hard");
    }

    /*
    This test confirms that the money was calculated correctly for the Easy difficulty
    and makes sure the difficulty was on the right setting
     */
    @Test
    public void testCorrectMoneyEasy() {
        clickOn("Start");
        write("Yo Mama");
        //This clicks on the drop down for difficulty
        clickOn("Medium");
        //This second one picks the actual difficulty you want
        clickOn("Easy");
        clickOn("Confirm");
        verifyThat("Money: ", NodeMatchers.isNotNull());
        //The money value is calculated by 500 * the difficulty multiplier which is
        /*
        1 for lmao
        2 for hard
        3 for medium
        4 for easy
        ex: easy mode money = 500 * 4 = 2000
         */
        int moneyValue = 500 * 4;
        assertEquals(Main.getMoney(), moneyValue);
        //Check to see if right difficulty
        assertEquals(Main.getDifficulty(), "Easy");
    }

    /*
    This test confirms that the money was calculated correctly for the Medium difficulty
    and makes sure the difficulty was on the right setting
     */
    @Test
    public void testCorrectMoneyMedium() {
        clickOn("Start");
        write("Yo Mama");
        clickOn("Confirm");
        verifyThat("Money: ", NodeMatchers.isNotNull());
        //The money value is calculated by 500 * the difficulty multiplier which is
        /*
        1 for lmao
        2 for hard
        3 for medium
        4 for easy
        ex: easy mode money = 500 * 4 = 2000
         */
        int moneyValue = 500 * 3;
        assertEquals(Main.getMoney(), moneyValue);
        //Check to see if right difficulty
        assertEquals(Main.getDifficulty(), "Medium");
    }

    /*
    This test confirms that the money was calculated correctly for the lmao difficulty
    and makes sure the difficulty was on the right setting
     */
    @Test
    public void testCorrectMoneyLmao() {
        clickOn("Start");
        write("Yo Mama");
        //This clicks on the drop down for difficulty
        clickOn("Medium");
        //This second one picks the actual difficulty you want
        clickOn("lmao");
        clickOn("Confirm");
        verifyThat("Money: ", NodeMatchers.isNotNull());
        //The money value is calculated by 500 * the difficulty multiplier which is
        /*
        1 for lmao
        2 for hard
        3 for medium
        4 for easy
        ex: easy mode money = 500 * 4 = 2000
         */
        int moneyValue = 500;
        assertEquals(Main.getMoney(), moneyValue);
        //Check to see if right difficulty
        assertEquals(Main.getDifficulty(), "lmao");
    }
    /*
    This tests the correct money by selecting multiple difficulties and
    changing them to see if it updates the starting money correctly.
     */

    @Test
    public void testCorrectMoneyMultiple() {
        clickOn("Start");
        write("Test");
        clickOn("Medium");
        clickOn("Easy");
        clickOn("Easy");
        clickOn("lmao");
        clickOn("Confirm");
        verifyThat("Money: ", NodeMatchers.isNotNull());
        int moneyValue = 500;
        assertEquals(Main.getMoney(), moneyValue);
    }

    //This test checks to see if "Move up" works
    @Test
    public void testMovementUP() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        short playerX;
        short playerY;
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_W);
        boolean testComplete = false;
        while (!(testComplete)) {
            Room room = Room.getGc().getRoom();
            playerX = room.getPlayer().getX();
            playerY = room.getPlayer().getY();
            //System.out.println("currentY: " + playerY);
            if (playerY < -200) {
                robot.keyRelease(KeyEvent.VK_W);
                testComplete = true;
                System.out.println("IM A BEAST");
            }
        }
    }

    //This test checks to see if "Move down" works
    @Test
    public void testMovementDown() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        short playerX;
        short playerY;
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_S);
        boolean testComplete = false;
        while (!(testComplete)) {
            Room room = Room.getGc().getRoom();
            playerX = room.getPlayer().getX();
            playerY = room.getPlayer().getY();
            //System.out.println("currentY: " + playerY);
            if (playerY > -200) {
                robot.keyRelease(KeyEvent.VK_S);
                testComplete = true;
                System.out.println("IM A BEAST");
            }
        }
    }

    //This test checks to see if "Move left" works
    @Test
    public void testMovementLeft() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        short playerX;
        short playerY;
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_A);
        boolean testComplete = false;
        while (!(testComplete)) {
            Room room = Room.getGc().getRoom();
            playerX = room.getPlayer().getX();
            playerY = room.getPlayer().getY();
            //System.out.println("currentY: " + playerY);
            if (playerX < -200) {
                robot.keyRelease(KeyEvent.VK_A);
                testComplete = true;
                System.out.println("IM A BEAST");
            }
        }
    }

    //This test checks to see if "Move right" works
    @Test
    public void testMovementRight() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        short playerX;
        short playerY;
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_D);
        boolean testComplete = false;
        while (!(testComplete)) {
            Room room = Room.getGc().getRoom();
            playerX = room.getPlayer().getX();
            playerY = room.getPlayer().getY();
            //System.out.println("currentY: " + playerY);
            if (playerX < 200) {
                robot.keyRelease(KeyEvent.VK_D);
                testComplete = true;
                System.out.println("IM A BEAST");
            }
        }
    }

    @Test
    /*
    This test checks to see if the ai will end up at the
    designated door if it tries to navigate to it
     */
    public void testDoor1() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        short playerX = 0;
        short playerY = 0;
        Robot robot = new Robot();
        //robot.keyPress(KeyEvent.VK_W);
        boolean testComplete = false;
        Room room = Room.getGc().getRoom();
        LinkedList<Pair<Integer, Integer>> doorList = room.getRoomDoors();
        //WHICH DOOR U WANNA GO TO index 0 =  door 1
        int targetDoorIndex = 0;
        Pair door = doorList.get(targetDoorIndex);
        //Integer x = (Integer) door.getKey();
        boolean atY = false;
        boolean atX = false;
        robot.setAutoDelay(200);
        while (!(testComplete)) {
            while (!(atX)) {
                playerX = room.getPlayer().getX();
                if (playerX >= (Integer) door.getKey() + 10) {
                    robot.keyPress(KeyEvent.VK_A);
                    robot.keyRelease(KeyEvent.VK_D);
                } else if (playerX <= (Integer) door.getKey() - 10) {
                    robot.keyPress(KeyEvent.VK_D);
                    robot.keyRelease(KeyEvent.VK_A);
                } else {
                    robot.keyRelease(KeyEvent.VK_A);
                    robot.keyRelease(KeyEvent.VK_D);
                    atX = true;
                }
            }
            robot.waitForIdle();
            while (!(atY)) {
                playerY = room.getPlayer().getY();
                System.out.println(playerY);
                if (playerY >= (Integer) door.getValue() + 30) {
                    robot.keyPress(KeyEvent.VK_W);
                    robot.keyRelease(KeyEvent.VK_S);
                } else if (playerY <= (Integer) door.getValue() - 30) {
                    robot.keyPress(KeyEvent.VK_S);
                    robot.keyRelease(KeyEvent.VK_W);
                } else {
                    robot.keyRelease(KeyEvent.VK_S);
                    robot.keyRelease(KeyEvent.VK_W);
                    atY = true;
                }
            }

            if (atX && atY) {
                System.out.println("ATX: " + atY);
                testComplete = true;
            }

        }
        int doorIndex = 99;
        for (Pair<Integer, Integer> currDoor : doorList) {
            if (Math.abs(playerX - currDoor.getKey())
                    < 20 && Math.abs(playerY - currDoor.getValue()) < 35) {
                doorIndex = doorList.indexOf(currDoor);
                System.out.println("Player is on door " + doorIndex);

            }
        }
        assertEquals(doorIndex, targetDoorIndex);

    }

    //Tests to see if the player can go out of bounds, off the map, moving straight to the left.
    @Test
    public void testOOBLeft() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");

        Robot robot = new Robot();

        Room room = Room.getGc().getRoom();
        boolean atX = false;
        robot.setAutoDelay(200);

        robot.keyPress(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_W);
        while (!atX) {
            robot.keyPress(KeyEvent.VK_A);
            if (room.getPlayer().getX() <= -600) {
                atX = true;
            }
        }
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        assertTrue(room.getPlayer().getX() > -610);

    }

    //This test the boundary to the right
    @Test
    public void testOOBRight() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");

        Robot robot = new Robot();

        Room room = Room.getGc().getRoom();
        boolean atX = false;
        robot.setAutoDelay(200);

        robot.keyPress(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_W);
        while (!atX) {
            robot.keyPress(KeyEvent.VK_D);
            if (room.getPlayer().getX() >= 605) {
                atX = true;
            }
        }
        robot.keyRelease(KeyEvent.VK_D);
        robot.keyPress(KeyEvent.VK_D);
        robot.keyRelease(KeyEvent.VK_D);
        robot.keyPress(KeyEvent.VK_D);
        robot.keyRelease(KeyEvent.VK_D);
        robot.keyPress(KeyEvent.VK_D);
        robot.keyRelease(KeyEvent.VK_D);
        assertTrue(room.getPlayer().getX() < 610);

    }

    @Test
    public void testOOBUp() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");

        Robot robot = new Robot();

        Room room = Room.getGc().getRoom();
        boolean atY = false;
        robot.setAutoDelay(200);

        while (!atY) {
            robot.keyPress(KeyEvent.VK_W);
            if (room.getPlayer().getY() <= -298) {
                atY = true;
            }
        }
        robot.keyRelease(KeyEvent.VK_W);
        robot.keyPress(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_W);
        robot.keyPress(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_W);
        robot.keyPress(KeyEvent.VK_W);
        robot.keyRelease(KeyEvent.VK_W);
        assertTrue(room.getPlayer().getY() > -303);

    }

    @Test
    public void testOOBDown() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");

        Robot robot = new Robot();

        Room room = Room.getGc().getRoom();
        boolean atY = false;
        robot.setAutoDelay(200);

        while (!atY) {
            robot.keyPress(KeyEvent.VK_S);
            if (room.getPlayer().getY() >= 268) {
                atY = true;
            }
        }
        robot.keyRelease(KeyEvent.VK_S);
        robot.keyPress(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_S);
        robot.keyPress(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_S);
        robot.keyPress(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_S);
        assertTrue(room.getPlayer().getY() < 269);

    }

    @Test
    /*
    This test checks to see if the ai will end up
    at the designated door if it tries to navigate to it
     */
    public void testDoor2() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        short playerX = 0;
        short playerY = 0;
        Robot robot = new Robot();
        //robot.keyPress(KeyEvent.VK_W);
        boolean testComplete = false;
        Room room = Room.getGc().getRoom();
        LinkedList<Pair<Integer, Integer>> doorList = room.getRoomDoors();
        //WHICH DOOR U WANNA GO TO index 0 =  door 1
        int targetDoorIndex = 1;
        Pair door = doorList.get(targetDoorIndex);
        //Integer x = (Integer) door.getKey();
        boolean atY = false;
        boolean atX = false;
        robot.setAutoDelay(200);
        Room startRoom = Room.getGc().getRoom();
        while (!(testComplete)) {
            while (!(atX)) {
                playerX = room.getPlayer().getX();
                if (playerX >= (Integer) door.getKey() + 10) {
                    robot.keyPress(KeyEvent.VK_A);
                    robot.keyRelease(KeyEvent.VK_D);
                } else if (playerX <= (Integer) door.getKey() - 10) {
                    robot.keyPress(KeyEvent.VK_D);
                    robot.keyRelease(KeyEvent.VK_A);
                } else {
                    robot.keyRelease(KeyEvent.VK_A);
                    robot.keyRelease(KeyEvent.VK_D);
                    atX = true;
                }
                if (Room.getGc().getRoom() != startRoom) {
                    atX = true;
                    atY = true;
                }
            }
            robot.waitForIdle();

            while (!(atY)) {
                playerY = room.getPlayer().getY();
                System.out.println(playerY);
                if (playerY >= (Integer) door.getValue() + 30) {
                    robot.keyPress(KeyEvent.VK_W);
                    robot.keyRelease(KeyEvent.VK_S);
                } else if (playerY <= (Integer) door.getValue() - 30) {
                    robot.keyPress(KeyEvent.VK_S);
                    robot.keyRelease(KeyEvent.VK_W);
                } else {
                    robot.keyRelease(KeyEvent.VK_S);
                    robot.keyRelease(KeyEvent.VK_W);
                    atY = true;
                }
                if (Room.getGc().getRoom() != startRoom) {
                    atY = true;
                }
            }

            if (atX && atY) {
                System.out.println("ATX: " + atY);
                testComplete = true;
            }

        }
        assertNotSame(Room.getGc().getRoom(), startRoom);

    }

    /*
    *
    * MILESTONE 4
    *
     */

    @Test
    public void testPlayerDies() {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        Room room = Room.getGc().getRoom();
        boolean testComplete = false;
        while (!testComplete) {
            if (room.getPlayer().getHealth() <= 0) {
                System.out.println("player dead");
                testComplete = true;
            } else {

                System.out.println("Health: " + room.getPlayer().getHealth());
            }
        }
        assertTrue(room.getPlayer().getHealth() <= 0);
    }

    @Test
    public void testMinion1Sprite() {
        Minion m = new Minion(Room.getGc().getRoom(),
                Room.getGc().getRoom().getPlayer(), (short) 1, (short) 0, (short) 0);
        assertEquals(m.getSprite().getImage(), new Image("res/Sprites/Minion.png"));
    }

    @Test
    public void testMinion2Sprite() {
        Minion m = new Minion(Room.getGc().getRoom(),
                Room.getGc().getRoom().getPlayer(), (short) 2, (short) 0, (short) 0);
        assertEquals(m.getSprite().getImage(), new Image("res/Sprites/FireMinion.png"));
    }

    @Test
    public void testMinion3Sprite() {
        Minion m = new Minion(Room.getGc().getRoom(),
                Room.getGc().getRoom().getPlayer(), (short) 1, (short) 0, (short) 0);
        assertEquals(m.getSprite().getImage(), new Image("res/Sprites/RockMinion.png"));
    }

    @Test
    public void testMinionMoveUp() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        short playerX;
        short playerY;
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_W);
        boolean testComplete = false;
        while (!(testComplete)) {
            Room room = Room.getGc().getRoom();
            playerX = room.getPlayer().getX();
            playerY = room.getPlayer().getY();
            //System.out.println("currentY: " + playerY);
            if (playerY < -200) {
                robot.keyRelease(KeyEvent.VK_W);
                testComplete = true;
                //System.out.println("IM A BEAST");
            }
        }
    }

    @Test
    public void testMinionMoveDown() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        short playerX;
        short playerY;
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_S);
        boolean testComplete = false;
        while (!(testComplete)) {
            Room room = Room.getGc().getRoom();
            playerX = room.getPlayer().getX();
            playerY = room.getPlayer().getY();
            //System.out.println("currentY: " + playerY);
            if (playerY > -200) {
                robot.keyRelease(KeyEvent.VK_S);
                testComplete = true;
                //System.out.println("IM A BEAST");
            }
        }
    }

    @Test
    public void testMinionMoveLeft() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        short playerX;
        short playerY;
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_A);
        boolean testComplete = false;
        while (!(testComplete)) {
            Room room = Room.getGc().getRoom();
            playerX = room.getPlayer().getX();
            playerY = room.getPlayer().getY();
            //System.out.println("currentY: " + playerY);
            if (playerX < -200) {
                robot.keyRelease(KeyEvent.VK_A);
                testComplete = true;
                //System.out.println("IM A BEAST");
            }
        }
    }

    @Test
    public void testMinionMoveRight() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        short playerX;
        short playerY;
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_D);
        boolean testComplete = false;
        while (!(testComplete)) {
            Room room = Room.getGc().getRoom();
            playerX = room.getPlayer().getX();
            playerY = room.getPlayer().getY();
            //System.out.println("currentY: " + playerY);
            if (playerX < 200) {
                robot.keyRelease(KeyEvent.VK_D);
                testComplete = true;
                //System.out.println("IM A BEAST");
            }
        }
    }

    /*
     *
     * MILESTONE 5
     *
     */


    @Test
    public void testDropWeapon() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");

        Robot robot = new Robot();


        robot.keyPress(KeyEvent.VK_Q);
        robot.keyPress(KeyEvent.VK_W);
        assertTrue(Room.getGc().getRoom().getPlayer().getWeapon() == null);

    }

    @Test
    public void testPickupWeapon() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");

        Room room = Room.getGc().getRoom();
        Player player = room.getPlayer();
        Weapon weapon = player.getWeapon();

        Robot robot = new Robot();

        robot.keyPress(KeyEvent.VK_Q);

        robot.keyPress(KeyEvent.VK_S);
        robot.keyPress(KeyEvent.VK_D);
        robot.keyPress(KeyEvent.VK_S);
        robot.keyPress(KeyEvent.VK_D);

        assertTrue(player.getInventory().contains(weapon));


    }


    @Test
    public void testInventoryUseItem() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        Player player = Room.getGc().getRoom().getPlayer();
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_E);
        robot.delay(2000);
        clickOn("Close Inventory");
        robot.delay(2000);
        robot.keyPress(KeyEvent.VK_E);
        robot.delay(2000);
        clickOn("Health Potion");
        robot.delay(250);
        clickOn("Close Inventory");
        robot.delay(250);
        robot.keyPress(KeyEvent.VK_E);
        for (Item item : player.getInventory().getItems()) {
            assertNotEquals("Health Potion", item.getName());
        }
        //Player player = Room.getGc().getRoom().getPlayer();
        //InventoryWindow inventoryWindow = player.getInventory().displayInventory();
        //inventoryWindow.getScene().getRoot();
    }

    @Test
    public void testInventoryAdd() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        Room room = Room.getGc().getRoom();
        Robot robot = new Robot();
        Inventory inventory = new Inventory();
        assertEquals(0, inventory.getItems().size());
        robot.keyPress(KeyEvent.VK_E);
        ImageView potionImage = new ImageView(new Image("res/Sprites/Potion2.png"));
        inventory.putInside(new Potion("potion that gets us an A on this milestone", potionImage,
                inventory, Room.getGc().getRoom().getPlayer(), (short) 0, (short) 0));
        assertEquals(1, inventory.getItems().size());
    }

    @Test
    public void testInventoryFull() {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        Inventory inventory = new Inventory();
        ImageView potionImg = new ImageView("res/sprites/potion1.png");
        inventory.putInside(new Potion("Test", potionImg,
                inventory, Room.getGc().getRoom().getPlayer(), (short) 0, (short) 0));
        inventory.putInside(new Potion("Test", potionImg,
                inventory, Room.getGc().getRoom().getPlayer(), (short) 0, (short) 0));
        inventory.putInside(new Potion("Test", potionImg,
                inventory, Room.getGc().getRoom().getPlayer(), (short) 0, (short) 0));
        inventory.putInside(new Potion("Test", potionImg,
                inventory, Room.getGc().getRoom().getPlayer(), (short) 0, (short) 0));
        inventory.putInside(new Potion("Test", potionImg,
                inventory, Room.getGc().getRoom().getPlayer(), (short) 0, (short) 0));
        inventory.putInside(new Potion("Test", potionImg,
                inventory, Room.getGc().getRoom().getPlayer(), (short) 0, (short) 0));
        assertEquals(false, inventory.putInside(new Potion("Test", potionImg,
                inventory, Room.getGc().getRoom().getPlayer(), (short) 0, (short) 0)));
        assertEquals(inventory.getCapacity(), inventory.getItems().size());
    }

    //DARIEN TESTS

    //TESTS IF INVENTORY OPENS
    @Test
    public void testInventoryOpen() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_E);

        Room room = Room.getGc().getRoom();

        while (room.getPlayer().getInventory().getInventoryWindow() == null) {
            continue;
        }
        System.out.println("done");
        clickOn(room.getPlayer().getInventory().getInventoryWindow());
        assertEquals(true, room.getPlayer().getInventory().getInventoryWindow().isShowing());


    }
    //TEST THAT CLOSING WORKS
    @Test
    public void testInventoryClose() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_E);

        Room room = Room.getGc().getRoom();

        while (room.getPlayer().getInventory().getInventoryWindow() == null) {
            continue;
        }
        System.out.println("done");
        clickOn(room.getPlayer().getInventory().getInventoryWindow());
        clickOn("Close Inventory");
        assertEquals(false, room.getPlayer().getInventory().getInventoryWindow().isShowing());

    }
    //TESTS THE STARTING POTION
    @Test
    public void testPotion() throws AWTException {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_E);

        Room room = Room.getGc().getRoom();

        while (room.getPlayer().getInventory().getInventoryWindow() == null) {
            continue;
        }
        System.out.println("done");
        clickOn(room.getPlayer().getInventory().getInventoryWindow());
        clickOn("Health Potion");
        //verifyThat("Health Potion", NodeMatchers.isNull());
    }

    //M6 Tests
    @Test
    public void testBossFull() {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        Room room = Room.getGc().getRoom();
        Boss b = new Boss(room, room.getPlayer(), (short) 0, (short) 0);
        assertEquals(b.getSprite(), new ImageView(new Image("res/Sprites/Boss/BossFull.png")));
    }

    @Test
    public void testBossThreeFourths() {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        Room room = Room.getGc().getRoom();
        Boss b = new Boss(room, room.getPlayer(), (short) 0, (short) 0);
        b.setHealth((short) (b.getHealth() * (3 / 4) - 1));
        assertEquals(b.getSprite(), new ImageView(new Image("res/Sprites/Boss/Boss3_4.png")));
    }

    @Test
    public void testBossOneHalf() {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        Room room = Room.getGc().getRoom();
        Boss b = new Boss(room, room.getPlayer(), (short) 0, (short) 0);
        b.setHealth((short) (b.getHealth() / 2 - 1));
        assertEquals(b.getSprite(), new ImageView(new Image("res/Sprites/Boss/Boss1_2.png")));
    }

    @Test
    public void testBossOneFourth() {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        Room room = Room.getGc().getRoom();
        Boss b = new Boss(room, room.getPlayer(), (short) 0, (short) 0);
        b.setHealth((short) (b.getHealth() / 4 - 1));
        assertEquals(b.getSprite(), new ImageView(new Image("res/Sprites/Boss/Boss1_4.png")));
    }

    @Test
    public void testBossZero() {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        Room room = Room.getGc().getRoom();
        Boss b = new Boss(room, room.getPlayer(), (short) 0, (short) 0);
        b.setHealth((short) 0);
        assertFalse(room.getMinions().contains(b));
    }

    @Test
    public void testStatsPopup() {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        Room room = Room.getGc().getRoom();
        Player player = room.getPlayer();
        player.setHealth((short) 0);
        Scenes.createStatsScreen();
        Popup stats = null;
        while (stats == null) {
            stats = Scenes.getStatsPopup();
        }
        assertTrue(Scenes.getStatsPopup() != null);
    }

    @Test
    public void testChallengePopup() {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        Room room = Room.getGc().getRoom();
        Player player = room.getPlayer();
        Scenes.createChallengePopup();
        Popup challenge = null;
        while (challenge == null) {
            challenge = Scenes.getChallengePopup();
        }
        assertTrue(Scenes.getStatsPopup() != null);
    }
    @Test
    public void testChallengeAccept() {
        clickOn("Start");
        write("Test");
        clickOn("Confirm");
        Room room = Room.getGc().getRoom();
        Player player = room.getPlayer();
        Scenes.createChallengePopup();
        Popup challenge = null;
        challenge = Scenes.getChallengePopup();
        clickOn("Accept");
        assertTrue(challenge == null && room.getMinions() != null);
    }

}




