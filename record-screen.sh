#!/bin/bash
# screencast scripts from https://gitlab.com/ccrpc/screencast

mkdir -p ~/Videos
filename=~/Videos/screencast-`date +"%Y-%m-%d-%H-%M-%S"`

echo Recording screen...
echo Press Ctrl + C to stop recording.

case "$(uname -s)" in
  Linux*)
  video_size=`xdpyinfo | grep -i dimensions: | sed 's/[^0-9]*pixels.*(.*).*//' | sed 's/[^0-9x]*//'`
  ffmpeg -hide_banner -loglevel panic -y \
    -f pulse -i "default" \
    -video_size "$video_size" -framerate 12 \
    -f x11grab -i ":0.0" \
    -c:v libx264 -crf 0 -preset ultrafast $filename.mkv
    ;;
  MINGW64*)
    audio_device=`ffmpeg -hide_banner -list_devices true -f dshow -i dummy 2>&1 >/dev/null | grep 'Microphone' | grep -oP '(?<=").*(?=")'`
    ffmpeg -hide_banner -loglevel panic -y \
      -f dshow -i audio="$audio_device" \
      -framerate 12 \
      -f gdigrab -i "desktop" \
      -c:v libx264 -crf 0 -preset ultrafast $filename.mkv
    ;;
  *)
    echo 'Platform not supported.'
    exit 1
    ;;
esac

echo
echo Encoding video...

ffmpeg -hide_banner -loglevel panic \
  -i $filename.mkv \
  -vf scale=1024:-1 -profile:v baseline -level 3.0 -pix_fmt yuv420p \
  -c:v libx264 -c:a aac -crf 30 -movflags faststart $filename.mp4

rm $filename.mkv

echo Done!
