package julie.study;

import android.content.Context;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UpdateControllerTest {

    UpdateController controller;

    @Mock
    Context mMockContext;

    @Test
    public void testCheckStartUpdate() throws Exception {
        controller=new UpdateController();
        controller.checkStartUpdate(mMockContext);
        Assert.assertTrue(controller.readyToUpdate);
    }




}