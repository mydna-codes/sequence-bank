package codes.mydna.api.configurations;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle(value = "mydnacodes.limits", watch = true)
public class ConfigUserLimits {

    @ConfigValue(value = "guest.max-dna-length")
    private int guestMaxDnaLength;
    
    @ConfigValue(value = "guest.max-enzyme-length")
    private int guestMaxEnzymeLength;
    
    @ConfigValue(value = "guest.max-gene-length")
    private int guestMaxGeneLength;

    @ConfigValue(value = "reg.max-dna-length")
    private int regMaxDnaLength;

    @ConfigValue(value = "reg.max-enzyme-length")
    private int regMaxEnzymeLength;

    @ConfigValue(value = "reg.max-gene-length")
    private int regMaxGeneLength;
    
    @ConfigValue(value = "pro.max-dna-length")
    private int proMaxDnaLength;

    @ConfigValue(value = "pro.max-enzyme-length")
    private int proMaxEnzymeLength;

    @ConfigValue(value = "pro.max-gene-length")
    private int proMaxGeneLength;

    // TODO: Return based on logged in user


    public int getGuestMaxDnaLength() {
        return guestMaxDnaLength;
    }

    public void setGuestMaxDnaLength(int guestMaxDnaLength) {
        this.guestMaxDnaLength = guestMaxDnaLength;
    }

    public int getGuestMaxEnzymeLength() {
        return guestMaxEnzymeLength;
    }

    public void setGuestMaxEnzymeLength(int guestMaxEnzymeLength) {
        this.guestMaxEnzymeLength = guestMaxEnzymeLength;
    }

    public int getGuestMaxGeneLength() {
        return guestMaxGeneLength;
    }

    public void setGuestMaxGeneLength(int guestMaxGeneLength) {
        this.guestMaxGeneLength = guestMaxGeneLength;
    }

    public int getRegMaxDnaLength() {
        return regMaxDnaLength;
    }

    public void setRegMaxDnaLength(int regMaxDnaLength) {
        this.regMaxDnaLength = regMaxDnaLength;
    }

    public int getRegMaxEnzymeLength() {
        return regMaxEnzymeLength;
    }

    public void setRegMaxEnzymeLength(int regMaxEnzymeLength) {
        this.regMaxEnzymeLength = regMaxEnzymeLength;
    }

    public int getRegMaxGeneLength() {
        return regMaxGeneLength;
    }

    public void setRegMaxGeneLength(int regMaxGeneLength) {
        this.regMaxGeneLength = regMaxGeneLength;
    }

    public int getProMaxDnaLength() {
        return proMaxDnaLength;
    }

    public void setProMaxDnaLength(int proMaxDnaLength) {
        this.proMaxDnaLength = proMaxDnaLength;
    }

    public int getProMaxEnzymeLength() {
        return proMaxEnzymeLength;
    }

    public void setProMaxEnzymeLength(int proMaxEnzymeLength) {
        this.proMaxEnzymeLength = proMaxEnzymeLength;
    }

    public int getProMaxGeneLength() {
        return proMaxGeneLength;
    }

    public void setProMaxGeneLength(int proMaxGeneLength) {
        this.proMaxGeneLength = proMaxGeneLength;
    }
}
