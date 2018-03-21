package galacticmail;

public class Manager {

    private Arena arena;
    // Arena object is initialized
    public Manager(Arena arena) {
        this.arena = arena;
    }
    // Returns arena object
    public Arena getArena() {
        return arena;
    }
    // Sets arena object
    public void setArena(Arena arena) {
        this.arena = arena;
    }
    // Return keyControl from arena
    public KeyControl getKeyControl() {
        return arena.getKeyControl();
    }
    // Returns mouseControl from arena
    public MouseControl getMouseControl() {
        return arena.getMouseControl();
    }
    // Returns background score
    public Sounds getBackgroundMusic() {
        return arena.getBackgroundMusic();
    }
    // Returns menu music
    public Sounds getMenuMusic() {
        return arena.getMenuMusic();
    }
    // Re-initializes and calls run
    public void reset() {
        arena.run();
    }
}
