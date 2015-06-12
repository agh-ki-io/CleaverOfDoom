package pl.edu.agh.game.settings;

import com.badlogic.gdx.Input.Keys;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-09
 */
public class DesktopInputSettings implements InputSettings {
    //movements
    public int moveLeftKey = Keys.A;
    public int moveUpKey = Keys.W;
    public int moveDownKey = Keys.S;
    public int moveRightKey = Keys.D;

    //skills
    public int skill1Key = Keys.U;
    public int skill2Key = Keys.I;
    public int skill3Key = Keys.O;
    public int skill4Key = Keys.P;

    //other
    public int menuKey = Keys.ESCAPE;
    public int confirmKey = Keys.ENTER;

    @Override
    public int getMoveLeftKey() {
        return moveLeftKey;
    }

    @Override
    public void setMoveLeftKey(int moveLeftKey) {
        this.moveLeftKey = moveLeftKey;
    }

    @Override
    public int getMoveUpKey() {
        return moveUpKey;
    }

    @Override
    public void setMoveUpKey(int moveUpKey) {
        this.moveUpKey = moveUpKey;
    }

    @Override
    public int getMoveDownKey() {
        return moveDownKey;
    }

    @Override
    public void setMoveDownKey(int moveDownKey) {
        this.moveDownKey = moveDownKey;
    }

    @Override
    public int getMoveRightKey() {
        return moveRightKey;
    }

    @Override
    public void setMoveRightKey(int moveRightKey) {
        this.moveRightKey = moveRightKey;
    }

    @Override
    public int getSkill1Key() {
        return skill1Key;
    }

    @Override
    public void setSkill1Key(int skill1Key) {
        this.skill1Key = skill1Key;
    }

    @Override
    public int getSkill2Key() {
        return skill2Key;
    }

    @Override
    public void setSkill2Key(int skill2Key) {
        this.skill2Key = skill2Key;
    }

    @Override
    public int getSkill3Key() {
        return skill3Key;
    }

    @Override
    public void setSkill3Key(int skill3Key) {
        this.skill3Key = skill3Key;
    }

    @Override
    public int getSkill4Key() {
        return skill4Key;
    }

    @Override
    public void setSkill4Key(int skill4Key) {
        this.skill4Key = skill4Key;
    }

    @Override
    public int getMenuKey() {
        return menuKey;
    }

    @Override
    public void setMenuKey(int menuKey) {
        this.menuKey = menuKey;
    }

    @Override
    public int getConfirmKey() {
        return confirmKey;
    }

    @Override
    public void setConfirmKey(int confirmKey) {
        this.confirmKey = confirmKey;
    }
}
