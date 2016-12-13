package me.gosimple.nbvcxz;

import me.gosimple.nbvcxz.scoring.Result;
import org.junit.Assert;
import org.junit.Test;

/**
 * Adam Brusselback
 */
public class NbvcxzTest
{

    /**
     * Test of estimate method, of class Nbvcxz.
     */
    @Test
    public void testEstimate()
    {
        String password;
        Result result;
        final double tolerance = 0.00000001;
        final Nbvcxz nbvcxz = new Nbvcxz();

        try
        {
            password = "correcthorsebatterystaple";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(16.60965490131509D, result.getEntropy(), 16.60965490131509D * tolerance);

            password = "a.b.c.defy";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(35.05294537608871D, result.getEntropy(), 35.05294537608871D * tolerance);

            password = "helpimaliveinhere";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(39.736275149583484D, result.getEntropy(), 39.736275149583484D * tolerance);

            password = "damnwindowsandpaper";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(31.086623767089435D, result.getEntropy(), 31.086623767089435D * tolerance);

            password = "zxcvbnm";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(5.321928094887363D, result.getEntropy(), 5.321928094887363D * tolerance);

            password = "1qaz2wsx3edc";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(10.523561956057012D, result.getEntropy(), 10.523561956057012D * tolerance);

            password = "temppass22";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(16.892495383759368D, result.getEntropy(), 16.892495383759368D * tolerance);

            password = "briansmith";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(4.321928094887363D, result.getEntropy(), 4.321928094887363D * tolerance);

            password = "thx1138";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(8.049848549450562D, result.getEntropy(), 8.049848549450562D * tolerance);

            password = "baseball2014";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(10.59618975614441D, result.getEntropy(), 10.59618975614441D * tolerance);

            password = "baseball1994";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(10.59618975614441D, result.getEntropy(), 10.59618975614441D * tolerance);

            password = "baseball2028";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(10.59618975614441D, result.getEntropy(), 10.59618975614441D * tolerance);

            password = "scorpions";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(12.938844457466171D, result.getEntropy(), 12.938844457466171D * tolerance);

            password = "ScoRpions";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(18.14829782309512D, result.getEntropy(), 18.14829782309512D * tolerance);

            password = "ScoRpi0ns";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(19.3817879533149D, result.getEntropy(), 19.3817879533149D * tolerance);

            password = "thereisneveragoodmonday";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(41.58420930363401D, result.getEntropy(), 41.58420930363401D * tolerance);

            password = "forgetthatchristmaspartytheotheryear";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(39.519621936739505D, result.getEntropy(), 39.519621936739505D * tolerance);

            password = "A Fool and His Money Are Soon Parted";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(90.52463096817459D, result.getEntropy(), 90.52463096817459D * tolerance);

            password = "6c891879ed0a0bbf701d5ca8af39a766";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(124.22235013869417D, result.getEntropy(), 124.22235013869417D * tolerance);

            password = "ef00623ced862e84ea15a6f97cb3fbb9f177bd6f23e54459a96ca5926c28c653";
            result = nbvcxz.estimate(password);
            Assert.assertEquals(247.06618865413472D, result.getEntropy(), 247.06618865413472D * tolerance);

        }
        catch (Exception e)
        {
            assert false;
        }
    }

}
