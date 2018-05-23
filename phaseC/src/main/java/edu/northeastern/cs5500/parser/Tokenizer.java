package edu.northeastern.cs5500.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<Integer,Integer> mappingLineNumbers =new HashMap<>();
    private Map<Integer,Integer> ReversemappingLineNumbers =new HashMap<>();


    public Map<Integer,Integer> getLineMapping(){
        return mappingLineNumbers;
    }

    public Map<Integer,Integer> getReverseLineMapping(){
        return ReversemappingLineNumbers;
    }


    /**
     *
     * @param withComments String array (Code Statements)
     * @return String Array
     * Convert String Array statements to one without comments and import statements
     */
    private String[] removeCommentsandImport(String[] withComments){
        logger.debug("Removing comments and import statements");
        String pattern1= "(?m)^ #.\n?";
        Pattern pat1= Pattern.compile(pattern1);
        Set<Integer> line=new HashSet<>();
        for(int i=0;i<withComments.length;i++){
            Matcher m1=pat1.matcher(withComments[i]);
            if(m1.matches()) {
                int x = withComments[i].indexOf('#');
                withComments[i] = withComments[i].replaceAll(withComments[i].substring(x), "");
                line.add(i);
            }
            if(withComments[i].contains("import")) {
                int x = withComments[i].indexOf("import");
                withComments[i] = withComments[i].replaceAll(withComments[i].substring(x), "");
                line.add(i);
            }

        }

        Iterator<Integer> itr=line.iterator();
        while(itr.hasNext()){
            int x=itr.next();
            for(int j=x+1;j<withComments.length;j++){
                int x1=getLineMapping().get(j);
                x1=x1-1;
                getLineMapping().put(j,x1);
            }
        }
        Iterator<Integer> itr1=line.iterator();
        int c=-1;
        while (itr1.hasNext()){
            int x=itr1.next();
            getLineMapping().put(x,c);
            c--;
        }

        for(Map.Entry<Integer,Integer> entry : mappingLineNumbers.entrySet()){
            ReversemappingLineNumbers.put(entry.getValue(),entry.getKey());
        }

        logger.debug("Removed comments and import statements");
        return withComments;
    }

    /**
     * Remove Whitespaces from the code
     * @param withWhiteSpaces Code with white spaces
     * @return String array without whitespaces
     */
    private String[] removeWhiteSpaces(String[] withWhiteSpaces){
        logger.debug("Removing whitespaces");
        String[] withoutspaces=new String[withWhiteSpaces.length];
        int c=0;
        for(int i=0;i<withWhiteSpaces.length;i++){
            withWhiteSpaces[i]=withWhiteSpaces[i].trim();
            if(withWhiteSpaces[i].isEmpty()||withWhiteSpaces[i]==null)
                continue;
            else
                withoutspaces[c++]=withWhiteSpaces[i];
        }
        logger.debug("Removed whitespaces");
        return withoutspaces;
    }


    /**
     * Convert the code to normalised code
     * @param Statement String[] code
     * @return String array
     */
    public String[] normaliseCode(String[] statement){
        logger.debug("Normalisation running");
        for(int i=0;i<statement.length;i++)
        	mappingLineNumbers.put(i,i);

        String[] withoutcomments= removeCommentsandImport(statement);
        List<String> code=new ArrayList<>();
        String[] withoutspace=removeWhiteSpaces(withoutcomments);
        for(int i=0;i<withoutspace.length;i++){
            if(withoutspace[i]!=null){
                code.add(withoutspace[i]);
            }
        }
        String[] normalise=new String[code.size()];
        normalise=code.toArray(normalise);

        logger.debug("Normalisation done");
        return normalise;
    }
    /**
     * Find all the variables in the Code
     * @param ip Code
     * @return Set of variables
     */
    public Set<String> findVariables(String[] ncode){
        Set<String> var=new HashSet<>();
        for(int i=0;i<ncode.length;i++){
            if(ncode[i]!=null&&ncode[i].contains("=")){
                ncode[i]=ncode[i].replaceAll("\\s+","");
                int x=ncode[i].indexOf('=');
                var.add(ncode[i].substring(0,x));
            }
        }
        logger.debug("variables found");
        return var;
    }

    /**
     * Function to find all functions in the String
     * @param ip Code
     * @return Set of Functions
     */
    public Set<String> findFunction(String[] ncode){
        Set<String> func=new HashSet<>();
        for(int i=0;i<ncode.length;i++) {
            if (ncode[i] != null && ncode[i].contains("def")) {
                int x=ncode[i].indexOf("def ");
                x=x+4;
                int y=ncode[i].indexOf('(');
                func.add(ncode[i].substring(x,y));
            }
        }
        logger.debug("functions found");
        return func;
    }

}