package pl.edu.agh.game.input;

import com.badlogic.gdx.audio.Music;
import pl.edu.agh.game.screens.MenuScreen;
import pl.edu.agh.game.screens.NewPlayableScreen;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-03
 */
public class InputState {
    private float xDirection;
    private float yDirection;
    private Music music;

    public void setMenu(MenuScreen menu) {
        this.menu = menu;
    }

    public void setScreen(NewPlayableScreen screen) {
        this.screen = screen;
    }


    private MenuScreen menu;
    private NewPlayableScreen screen;

//    private boolean menuOn;
    private boolean skill1Used;
    private boolean skill2Used;
    private boolean skill3Used;
    private boolean skill4Used;

    public float getxDirection() {
        return xDirection;
    }

    public void setxDirection(float xDirection) {
        this.xDirection = xDirection;
    }

    public float getyDirection() {
        return yDirection;
    }

    public void setyDirection(float yDirection) {
        this.yDirection = yDirection;
    }

//    public void setMenuOn(boolean menuOn) {
//        this.menuOn = menuOn;
//    }

    void setSkill1Used(boolean skill1Used) {
        this.skill1Used = skill1Used;
    }

    void setSkill2Used(boolean skill2Used) {
        this.skill2Used = skill2Used;
    }

    void setSkill3Used(boolean skill3Used) {
        this.skill3Used = skill3Used;
    }

    void setSkill4Used(boolean skill4Used) {
        this.skill4Used = skill4Used;
    }

//    public boolean isMenuOn() {
//        return menuOn;
//    }

    public void startGame() {
        menu.setGame(new NewPlayableScreen(menu.getGame()));
    }

    public void showMenu() {
        screen.showMenu();
    }
    
    public boolean isSkill1Used() {
        if (skill1Used) {
            setSkill1Used(false);
            return true;
        } else return false;
    }

    public boolean isSkill2Used() {
        if (skill2Used) {
            setSkill2Used(false);
            return true;
        } else return false;
    }

    public boolean isSkill3Used() {
        if (skill3Used) {
            setSkill3Used(false);
            return true;
        } else return false;
    }

    public boolean isSkill4Used() {
        if (skill4Used) {
            setSkill4Used(false);
            return true;
        } else return false;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public Music getMusic() {
        return music;
    }
//    public void setCurrentLevel(NewPlayableScreen currentLevel) {
//        this.currentLevel = currentLevel;
//    }
}
