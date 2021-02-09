# scout
Fast substring matching

Anand Natrajan
9/Feb/2021

The Scout algorithm is a new algorithm for finding a pattern string inside a target string. This open-source version implements it as an alternative to the `indexOf` method available as a Java built-in.

The `indexOf` method finds the first location of the pattern in the target. The method takes two parameters. The first, called `target`, is the sequence of characters to check. The second, called `pattern`, is the sequence of characters to find. The method returns the first index of the pattern, or -1 if the pattern is absent in the target.

The Scout implementation is described and compared against alternative algorithms in http://www.anandnatrajan.com/papers/CACM20a.pdf. This paper has been submitted to Communications of the ACM for publication under the title "Scout Algorithm for Fast Substring Matching", by A. Natrajan and M. Anand. A longer version with a detailed walkthrough of the algorithm and expanded comparisons is at arXiv, and can also be downloaded from http://www.anandnatrajan.com/papers/ARXIV20.pdf.

The algorithm and test cases can be compiled with `javac com/amobee/commons/utils/StringUtils.java`. There are no dependencies required other than a standard Java installation. The command `java com/amobee/commons/utils/StringUtils` can be run subsequently to run several test cases that compare the output of the Scout version of indexOf against the Java built-in version.

The self-contained snippet below shows how to use the method. Cut and paste it into a file named `SUTest.java`, and compile and run it with the appropriate classpath to see it work.
```
import com.amobee.commons.utils.StringUtils;

public class SUTest
{
	public static void main(String... args)
	{
		System.out.println("result = " + StringUtils.indexOf("hello", "yt"));
	}
}
```

The algorithm is made freely available to everyone under the MIT Licence. Please direct comments to github@anandnatrajan.com. Thank you.
