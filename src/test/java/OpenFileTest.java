import com.winxaito.gcodeeditor.utils.OpenFile;
import org.hamcrest.core.IsNot;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by: WinXaito (Kevin Vuilleumier)
 */
public class OpenFileTest{
    @Test
    public void testOpenFile(){
        OpenFile openFile = new OpenFile(new File(OpenFileTest.class.getResource("gcode.g").toString()));
        try{
            assertThat("", IsNot.not(openFile.getFileContent()));
        }catch(FileNotFoundException e){
            fail(e.getMessage());
        }
    }
}
