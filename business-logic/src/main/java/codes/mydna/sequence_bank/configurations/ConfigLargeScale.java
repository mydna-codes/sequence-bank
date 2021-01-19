package codes.mydna.sequence_bank.configurations;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle(value = "mydnacodes.thresholds.large-scale", watch = true)
public class ConfigLargeScale {

    @ConfigValue(value = "min-seq-length")
    private int minSeqLength;

    public int getMinSeqLength() {
        return minSeqLength;
    }

    public void setMinSeqLength(int minSeqLength) {
        this.minSeqLength = minSeqLength;
    }
}
