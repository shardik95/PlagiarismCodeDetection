package edu.northeastern.cs5500.antlr;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import edu.northeastern.cs5500.parser.Python3Lexer;
import edu.northeastern.cs5500.parser.Python3Parser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class ParserFacade {

    /**
     *
     * @param file Source code file
     * @param encoding
     * @return String
     * @throws IOException if unable to read file
     */
    private static String readFile(File file, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(file.toPath());
        return new String(encoded, encoding);
    }

    /**
     *
     * @param file Source code file
     * @return
     * @throws IOException
     */
    public Python3Parser.File_inputContext parse(File file) throws IOException {
        String code = readFile(file, Charset.forName("UTF-8"));
        Python3Lexer lexer = new Python3Lexer(new ANTLRInputStream(code));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        Python3Parser parser = new Python3Parser(tokens);

        return parser.file_input();
    }
}
