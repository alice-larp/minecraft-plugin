package in.aerem.AlicePlugin;

public class HomeAssistantState {
    public int[] rgb_color = new int[3];

    public HomeAssistantState(int r, int g, int b) {
        this.rgb_color[0] = r;
        this.rgb_color[1] = g;
        this.rgb_color[2] = b;
    }
}
