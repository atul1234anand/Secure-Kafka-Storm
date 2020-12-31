package com.kafkastuff.wordcount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

public class splitter extends BaseRichBolt {

	OutputCollector collector;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		String sentence = input.getValue(4).toString();
		//System.out.println("Sentence : "+input.getValue(4));
		
		if (sentence != null) {
			System.out.println("Sentence: " + sentence);
			String[] wordArray = sentence.split(" ");
			Map<String, Integer> wordMap = new HashMap<>();
			for (String word : wordArray) {
				Integer count = wordMap.get(word);
				if (count == null) {
					count = 0;
				}
				count++;
				wordMap.put(word, count);
			}
			// send the constructed Map to next bolt 
			System.out.println("Wordmap is: "+wordMap);
			collector.emit(new Values(wordMap));
		}
		// acknowledge that the processing of this tuple is finished
		collector.ack(input);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("wordMap"));
	}
}

