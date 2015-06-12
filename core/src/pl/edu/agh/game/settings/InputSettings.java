package pl.edu.agh.game.settings;

/**
 * @author - Lukasz Gmyrek
 *         Created on  2015-04-09
 */
public interface InputSettings {
    int getMoveLeftKey();

    void setMoveLeftKey(int moveLeftKey);

    int getMoveUpKey();

    void setMoveUpKey(int moveUpKey);

    int getMoveDownKey();

    void setMoveDownKey(int moveDownKey);

    int getMoveRightKey();

    void setMoveRightKey(int moveRightKey);

    int getSkill1Key();

    void setSkill1Key(int skill1Key);

    int getSkill2Key();

    void setSkill2Key(int skill2Key);

    int getSkill3Key();

    void setSkill3Key(int skill3Key);

    int getSkill4Key();

    void setSkill4Key(int skill4Key);

    int getMenuKey();

    void setMenuKey(int menuKey);

    int getConfirmKey();

    void setConfirmKey(int confirmKey);
}
