FileName = 'table1view.csv';

[~,SheetNames] = xlsfinfo(FileName);
if any(strcmp(SheetNames, 'table1view'))==1
  [a,b] = xlsread(FileName,'table1view');
else
  disp('Looking for sheet "table1view", but sheet not found') 
  return
end

revs = a(:,2);
freq = a(:,1);
nouns = b(:,1);
%for k = 1:size(revs,1)
nounsort = zeros(size(revs));
newfreq = zeros(size(freq));
srtrevs = sort(revs);
srtfreq = sort(freq);
% for k = 1:size(revs,1)
%     tmp = find(srtrevs==k);
% %     nounsort(k) = max(tmp,0);
%     newfreq(k) = freq(max(tmp'));
% end
% maxfreq = max(freq);
data = srtrevs(4700:end,1);
edge = 1:10:max(data');
histogram(edge,data);
title('Number of nouns falling into different catagories of number of reviews')
xlabel('Number of Nouns');
ylabel('Number of Reviews');