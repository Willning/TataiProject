#! /bin/bash
ffmpeg -f alsa -i hw:0 -t 2 -acodec pcm_s16le -ar 22050 -ac 1 foo.wav; HVite -H HMMs/hmm15/macros -H HMMs/hmm15/hmmdefs -C user/configLR  -w user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0  user/dictionaryD user/tiedList foo.wav; aplay foo.wav; rm foo.wav; more recout.mlf;
