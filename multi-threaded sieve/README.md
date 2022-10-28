Actual Output & Runtimes:

Intel(R) Core(TM) i7-8750H CPU @ 2.20GHz

No, the time does not scale linearly with the number of threads. 

./mtsieve -s100 -e200000000 -t1 : 0m4.959s

./mtsieve -s100 -e200000000 -t2 : 0m4.841s

./mtsieve -s100 -e200000000 -t3 : 0m5.025s

./mtsieve -s100 -e200000000 -t4 : 0m5.316s

./mtsieve -s100 -e200000000 -t5 : 0m5.923s

./mtsieve -s100 -e200000000 -t6 : 0m6.321s

./mtsieve -s100 -e200000000 -t7 : 0m6.992s

./mtsieve -s100 -e200000000 -t8 : 0m7.318s

As shown above, the real time decreased when going from 1 core to 2 cores, but then started escalating. 4 Cores
ended up taking longer than just 1 core, and 8 cores took longer than 4 cores, which I find really odd. 
After doing some research, it appears my processor is only capable of the full 2.2GHz when only 1 core is active, 
so although more threads are being used, they are each running at a slower rate. 