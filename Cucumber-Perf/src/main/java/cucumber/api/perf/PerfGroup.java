package cucumber.api.perf;

import java.util.ArrayList;

import cucumber.api.perf.salad.ast.Count;
import cucumber.api.perf.salad.ast.Group;
import cucumber.api.perf.salad.ast.Runners;
import cucumber.api.perf.salad.ast.Slice;
import gherkin.ast.DataTable;
import gherkin.ast.Node;
import gherkin.ast.TableRow;

public class PerfGroup {
	private final String keyword;
	private final String text;
	private int threads;
	private int maxThreads;
	private int count;
	private int running=0;
	private int ran = 0;
	private DataTable slices;
	
	 public PerfGroup(String keyword, String text,int threads, int count,DataTable slices) {
	        this.keyword = keyword;
	        this.text = text;
	        this.threads = threads;
	        this.count = count;
	        this.maxThreads = threads;
	        this.slices = slices;
	}
	 
	 public PerfGroup(Group group) {
	        this.keyword = group.getKeyword();
	        this.text = group.getText();
	        for (Node a : group.getArguments())
	        {
	        	if (a instanceof DataTable)
	        	{
	        		this.slices = ((DataTable) a);
	        	}
	        	else if (a instanceof Count)
	        	{
	        		this.count = Integer.parseInt(((Count) a).getContent());
	        	}
	        	else if (a instanceof Runners)
	        	{
	        		this.threads = Integer.parseInt(((Runners) a).getContent());
	        		this.maxThreads = threads;
	        	}
	        	
	        }
	}


	public int getMaxThreads() {
		return maxThreads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public int getRunning() {
		return running;
	}
	public String getKeyword() {
		return keyword;
	}

	public String getText() {
		return text;
	}

	public void incrementRunning() {
		this.running++;
	}
	
	public void decrementRunning() {
		this.running--;
	}
	
	public int getRan() {
		return ran;
	}
	public void incrementRan() {
		this.ran++;
	}
	public int getThreads() {
		return threads;
	}
	public int getCount() {
		return count;
	}
	public DataTable getSlices() {
		return slices;
	}
	public Slice getSlice()
	{
		ArrayList<TableRow> rows = new ArrayList<TableRow>();
		if (slices != null && slices.getRows().size()>1) {
			rows.add(slices.getRows().get(0));
			int sel = (ran % slices.getRows().size());
			if (sel<slices.getRows().size()-1) 
				sel++;
			rows.add(slices.getRows().get(sel));
			ran++;
			return new Slice(rows);
		}
		ran++;
		return null;
	}

}
